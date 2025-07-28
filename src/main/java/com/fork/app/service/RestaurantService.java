package com.fork.app.service;

import com.fork.app.domain.dto.request.MenuRequestDto;
import com.fork.app.domain.dto.request.RestaurantRequestDto;
import com.fork.app.domain.dto.response.MenuResponseDto;
import com.fork.app.domain.dto.response.RestaurantDetailResponseDto;
import com.fork.app.domain.dto.response.RestaurantListResponseDto;
import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import com.fork.app.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    // 카테고리로 식당 리스트 반환 (DTO에 맞게)
    public List<RestaurantListResponseDto> getRestaurantsByCategory(String categoryName) {
        RestaurantCategoryEnum categoryEnum = RestaurantCategoryEnum.valueOf(categoryName);
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByCategory(categoryEnum);

        return restaurants.stream()
                .map(restaurant -> {
                    // 메뉴에서 이미지 URL만 추출
                    List<String> menuImageUrls = restaurant.getMenus().stream()
                            .map(Menu::getImgUrl)
                            .collect(Collectors.toList());

                    return RestaurantListResponseDto.builder()
                            .id(restaurant.getRestaurantId())
                            .name(restaurant.getName())
                            .menus(menuImageUrls) // 이미지 URL 리스트
                            .rating(restaurant.getRating())
                            .reviewCount(restaurant.getReviewCount())
                            .hasAR(restaurant.isHasAR())       // Restaurant 엔티티에 boolean 필드라고 가정
                            .hasCoupon(restaurant.isHasCoupon()) // 마찬가지로 boolean 필드
                            .build();
                })
                .collect(Collectors.toList());
    }


    public RestaurantDetailResponseDto getRestaurantDetail(Long restaurantId) {
        // 1. 식당 정보 조회
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("없는 식당입니다."));
        // 🔸 Lazy 로딩된 storePictureUrl 강제 초기화
        List<String> storePictureUrl = new ArrayList<>(restaurant.getStorePictureUrl());

        // 2. 메뉴 리스트 조회
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);

        // 3. DTO 변환
        List<MenuResponseDto> menuResponseDtos = menus.stream()
                .map(menu -> MenuResponseDto.builder()
                        .menuId(menu.getMenuId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .category(menu.getCategory())
                        .build())
                .collect(Collectors.toList());

        // 4. 카테고리별 그룹핑
        Map<String, List<MenuResponseDto>> categorizedMenus = menuResponseDtos.stream()
                .collect(Collectors.groupingBy(dto -> dto.getCategory().getDisplayName()));
        // 또는 .name()으로 원시 Enum 이름 사용

        // 카테고리별 그룹핑 후, Enum 순서대로 LinkedHashMap에 담기
        Map<String, List<MenuResponseDto>> sortedCategorizedMenus = new LinkedHashMap<>();

        Arrays.stream(MenuCategoryEnum.values())
                .sorted(Comparator.comparingInt(MenuCategoryEnum::getOrder))
                .forEach(enumValue -> {
                    String displayName = enumValue.getDisplayName();
                    if (categorizedMenus.containsKey(displayName)) {
                        sortedCategorizedMenus.put(displayName, categorizedMenus.get(displayName));
                    }
                });

        // 5. 최종 응답 DTO 생성
        return RestaurantDetailResponseDto.builder()
                .id(restaurant.getRestaurantId())
                .name(restaurant.getName())
                .rating(restaurant.getRating())
                .reviewCount(restaurant.getReviewCount())
                .menus(sortedCategorizedMenus)
                .storePictureUrl(storePictureUrl)
                .build();
    }

    //메뉴 상세 반환
    public MenuResponseDto getMenuByRestaurantAndMenuId(Long restaurantId, Long menuId){
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);
        MenuResponseDto menuResponseDto = menus.stream()
                .filter(menu -> menu.getMenuId().equals(menuId))
                .findFirst()
                .map(menu -> MenuResponseDto.builder()
                        .menuId(menu.getMenuId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .category(menu.getCategory())
                        .imgUrl(menu.getImgUrl())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
        return menuResponseDto;
    }

    public void createRestaurantWithMenus(RestaurantRequestDto requestDto) {
        // 1. Restaurant 엔티티 생성
        Restaurant restaurant = Restaurant.builder()
                .name(requestDto.getName())
                .deliveryTip(requestDto.getDeliveryTip())
                .address(requestDto.getAddress())
                .storePictureUrl(requestDto.getStorePictureUrl())
                .minDeliveryTime(requestDto.getMinDeliveryTime())
                .maxDeliveryTime(requestDto.getMaxDeliveryTime())
                .minDeliveryPrice(requestDto.getMinDeliveryPrice())
                .hasCoupon(requestDto.getHasCoupon())
                .createdDate(LocalDateTime.now())
                .rating(requestDto.getRating())
                .restaurantCategoryEnum(RestaurantCategoryEnum.valueOf(requestDto.getRestaurantCategory()))
                .reviewCount(requestDto.getReviewCount())
                .build();
        restaurant.getMenus().size();
        Map<String, List<MenuRequestDto>> menusMap = requestDto.getMenus();
        List<Menu> menuEntities = new ArrayList<>();
        for (Map.Entry<String, List<MenuRequestDto>> entry : menusMap.entrySet()) {
            String categoryName = entry.getKey(); // "대표메뉴", "세트메뉴" 등
            MenuCategoryEnum categoryEnum = MenuCategoryEnum.valueOf(categoryName); // 반드시 Enum 이름과 일치해야 함

            for (MenuRequestDto dto : entry.getValue()) {
                Menu menu = Menu.builder()
                        .name(dto.getName())
                        .price(dto.getPrice())
                        .imgUrl(dto.getImgUrl())
                        .category(categoryEnum)
                        .restaurant(restaurant)
                        .build();
                menuEntities.add(menu);
            }
        }

        restaurant.getMenus().addAll(menuEntities); // 역방향 설정 (필요 시)

        // 3. 저장 (cascade 설정되어 있으므로 menu 자동 저장됨)
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurantImages(Long restaurantId, List<String> imageUrls) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));

        restaurant.setStorePictureUrl(imageUrls);
        // 변경감지(dirty checking)로 트랜잭션 끝나면 자동 반영됨
    }

}

