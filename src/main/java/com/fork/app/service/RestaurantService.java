package com.fork.app.service;

import com.fork.app.domain.dto.MenuResponseDto;
import com.fork.app.domain.dto.RestaurantResponseDto;
import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import com.fork.app.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    // 카테고리로 식당 리스트 반환
    public List<RestaurantResponseDto> getRestaurantsByCategory(String categoryname) {
        log.info("서비스 getRestaurantsByCategory메서드 시작");
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByCategory(RestaurantCategoryEnum.valueOf(categoryname));

        return restaurants.stream()
                .map(restaurant -> {
                    restaurant.getStorePictureUrl().size();
                    List<MenuResponseDto> menuResponseDtos = restaurant.getMenus().stream()
                            .map(menu -> MenuResponseDto.builder()
                                    .menuId(menu.getMenuId())
                                    .name(menu.getName())
                                    .price(menu.getPrice())
                                    .imgUrl(menu.getImgUrl())
                                    .build()
                            ).collect(Collectors.toList());

                    return RestaurantResponseDto.builder()
                            .restaurantId(restaurant.getRestaurantId())
                            .name(restaurant.getName())
                            .restaurantCategoryEnum(restaurant.getRestaurantCategoryEnum())
                            .address(restaurant.getAddress())
                            .menus(menuResponseDtos)  // 메뉴 리스트 포함
                            .storePictureUrl(restaurant.getStorePictureUrl())
                            .phone(restaurant.getPhone())
                            .rating(restaurant.getRating())
                            .reviewCount(restaurant.getReviewCount())
                            .createdDate(restaurant.getCreatedDate())
                            .build();
                })
                .collect(Collectors.toList());
    }


    //식당의 메뉴들 반환
    public Map<MenuCategoryEnum, List<MenuResponseDto>> getMenusOfRestaurant(Long restaurantId) {
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);
        List<MenuResponseDto> menuResponseDtos = menus.stream()
                .map(menu -> MenuResponseDto.builder()
                        .menuId(menu.getMenuId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .category(menu.getCategory())
                        .build())
                .collect(Collectors.toList());

        // 카테고리별로 그룹핑
        return menuResponseDtos.stream()
                .collect(Collectors.groupingBy(MenuResponseDto::getCategory));
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
}

