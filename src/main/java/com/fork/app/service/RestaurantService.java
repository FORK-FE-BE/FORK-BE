package com.fork.app.service;

import com.fork.app.domain.dto.request.MenuRequestDto;
import com.fork.app.domain.dto.request.RestaurantRequestDto;
import com.fork.app.domain.dto.response.MenuResponseDto;
import com.fork.app.domain.dto.response.RestaurantDetailResponseDto;
import com.fork.app.domain.dto.response.RestaurantListResponseDto;
import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.RAddress;

import com.fork.app.domain.entity.MenuCategory;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import com.fork.app.repository.RestaurantRepository;
import com.fork.app.service.KakaoMapService.Coordinate;
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
    private final KakaoMapService kakaoMapService;

    public List<RestaurantListResponseDto> getRestaurantsByCategory(RestaurantCategoryEnum category) {
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByCategory(category);

        List<Long> menuCategoryIds = restaurants.stream()
                .flatMap(r -> r.getMenuCategories().stream())
                .map(MenuCategory::getId)
                .collect(Collectors.toList());

        List<Menu> menus = restaurantRepository.findMenusByCategoryIds(menuCategoryIds);

        Map<Long, List<Menu>> menusGroupedByCategoryId = menus.stream()
                .collect(Collectors.groupingBy(menu -> menu.getCategory().getId()));

        // ✅ 메뉴 전체를 모아서 DTO에 넘김
        return restaurants.stream()
                .map(restaurant -> {
                    List<Menu> restaurantMenus = restaurant.getMenuCategories().stream()
                            .flatMap(category2 -> menusGroupedByCategoryId
                                    .getOrDefault(category2.getId(), List.of())
                                    .stream())
                            .collect(Collectors.toList());

                    return restaurant.toListDto(restaurantMenus); // 메뉴 전체 전달
                })
                .collect(Collectors.toList());
    }

    public  RestaurantDetailResponseDto getRestaurantDetail(Long restaurantId) {
        // 1. 식당 정보 조회
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("없는 식당입니다."));

        // 2. Lazy 로딩 강제 초기화
        List<String> storePictureUrl = new ArrayList<>(restaurant.getStorePictureUrl());

        // 3. 카테고리 → 메뉴 리스트 변환
        Map<String, List<MenuResponseDto>> categorizedMenus = getCategorizedMenus(restaurant);

        // 4. 최종 DTO 생성 및 반환
        return restaurant.toDetailDto(categorizedMenus);
    }

    //메뉴 상세 반환
    public MenuResponseDto getMenuByRestaurantAndMenuId(Long restaurantId, Long menuId) {
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);
        MenuResponseDto menuResponseDto = menus.stream()
                .filter(menu -> menu.getMenuId().equals(menuId))
                .findFirst()
                .map(Menu::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
        return menuResponseDto;
    }

    public void createRestaurantWithMenus(RestaurantRequestDto requestDto) {
        Restaurant restaurant = getRestaurant(requestDto);

        // 2. 메뉴 카테고리별로 구성
        Map<String, List<MenuRequestDto>> menusMap = requestDto.getMenus();
        List<MenuCategory> categoryEntities = new ArrayList<>();

        setCategoryEntities(menusMap, restaurant, categoryEntities);
        restaurant.setMenuCategories(categoryEntities);
        // 3. 저장 (CascadeType.ALL이므로 카테고리와 메뉴도 같이 저장됨)
        restaurantRepository.save(restaurant);
    }

    //식당 이미지 수정
    public void updateRestaurantImages(Long restaurantId, List<String> imageUrls) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));

        restaurant.setStorePictureUrl(imageUrls);
        // 변경감지(dirty checking)로 트랜잭션 끝나면 자동 반영됨
    }

//    private List<RestaurantListResponseDto> getRestaurantList(List<Restaurant> restaurants) {
//        return restaurants.stream()
//                .map(restaurant -> {
//                    // 메뉴에서 이미지 URL만 추출
//                    List<String> menuImgUrls = restaurant.getMenuCategories().stream()
//                            .flatMap(category -> category.getMenus().stream())
//                            .map(Menu::getImgUrl)
//                            .filter(Objects::nonNull)
//                            .collect(Collectors.toList());
//
//                    return restaurant.toListDto(menuImgUrls);
//                })
//                .collect(Collectors.toList());
//    }
    private Map<String, List<MenuResponseDto>> getCategorizedMenus(Restaurant restaurant) {
        Map<String, List<MenuResponseDto>> categorizedMenus = restaurant.getMenuCategories().stream()
                .collect(Collectors.toMap(
                        MenuCategory::getName,
                        category -> category.getMenus().stream()
                                .map(Menu::entityToDto)
                                .collect(Collectors.toList()),
                        (existing, replacement) -> existing, // 중복 키 처리
                        LinkedHashMap::new // 입력 순서 유지
                ));
        return categorizedMenus;
    }
    private Restaurant getRestaurant(RestaurantRequestDto requestDto) {
        Optional<Coordinate> coordOpt = kakaoMapService.getCoordinateFromAddress(
                requestDto.getFullRoadAddress()
        );
        Coordinate coord = coordOpt.orElseThrow(() -> new RuntimeException("주소로 좌표를 찾을 수 없습니다."));

        // 2. 주소 객체 생성
        RAddress rAddress = RAddress.builder()
                .province(requestDto.getAddress().getProvince())
                .city(requestDto.getAddress().getCity())
                .roadName(requestDto.getAddress().getRoadName())
                .buildingNumber(requestDto.getAddress().getBuildingNumber())
                .detail(requestDto.getAddress().getDetail())
                .postalCode(requestDto.getAddress().getPostalCode())
                .latitude(Double.parseDouble(coord.latitude()))
                .longitude(Double.parseDouble(coord.longitude()))
                .build();

        return Restaurant.builder()
                .name(requestDto.getName())
                .deliveryTip(requestDto.getDeliveryTip())
                .storePictureUrl(requestDto.getStorePictureUrl())
                .minDeliveryTime(requestDto.getMinDeliveryTime())
                .maxDeliveryTime(requestDto.getMaxDeliveryTime())
                .minDeliveryPrice(requestDto.getMinDeliveryPrice())
                .hasCoupon(requestDto.getHasCoupon())
                .createdDate(LocalDateTime.now())
                .address(rAddress)
                .rating(requestDto.getRating())
                .restaurantCategoryEnum(RestaurantCategoryEnum.valueOf(requestDto.getRestaurantCategory()))
                .reviewCount(requestDto.getReviewCount())
                .build();
    }

    private void setCategoryEntities(Map<String, List<MenuRequestDto>> menusMap, Restaurant restaurant, List<MenuCategory> categoryEntities) {
        for (Map.Entry<String, List<MenuRequestDto>> entry : menusMap.entrySet()) {
            String categoryName = entry.getKey(); // ex: "대표메뉴"
            MenuCategory menuCategory = new MenuCategory();
            menuCategory.setName(categoryName);
            menuCategory.setRestaurant(restaurant);

            List<Menu> menuList = entry.getValue().stream()
                    .map(dto -> Menu.builder()
                            .name(dto.getName())
                            .price(dto.getPrice())
                            .imgUrl(dto.getImgUrl())
                            .category(menuCategory)
                            .restaurant(restaurant)
                            .build())
                    .toList();

            menuCategory.setMenus(menuList);
            categoryEntities.add(menuCategory);
        }
    }
}

