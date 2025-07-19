package com.fork.app.service;

import com.fork.app.domain.dto.MenuDto;
import com.fork.app.domain.dto.RestaurantDto;
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
    public List<RestaurantDto> getRestaurantsByCategory(String categoryname) {
        log.info("서비스 getRestaurantsByCategory메서드 시작");
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByCategory(RestaurantCategoryEnum.valueOf(categoryname));

        return restaurants.stream()
                .map(restaurant -> {
                    restaurant.getStorePictureUrl().size();
                    List<MenuDto> menuDtos = restaurant.getMenus().stream()
                            .map(menu -> MenuDto.builder()
                                    .menuId(menu.getMenuId())
                                    .name(menu.getName())
                                    .price(menu.getPrice())
                                    .imgUrl(menu.getImgUrl())
                                    .build()
                            ).collect(Collectors.toList());

                    return RestaurantDto.builder()
                            .restaurantId(restaurant.getRestaurantId())
                            .name(restaurant.getName())
                            .restaurantCategoryEnum(restaurant.getRestaurantCategoryEnum())
                            .address(restaurant.getAddress())
                            .menus(menuDtos)  // 메뉴 리스트 포함
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
    public Map<MenuCategoryEnum, List<MenuDto>> getMenusOfRestaurant(Long restaurantId) {
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);
        List<MenuDto> menuDtos = menus.stream()
                .map(menu -> MenuDto.builder()
                        .menuId(menu.getMenuId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imgUrl(menu.getImgUrl())
                        .category(menu.getCategory())
                        .build())
                .collect(Collectors.toList());

        // 카테고리별로 그룹핑
        return menuDtos.stream()
                .collect(Collectors.groupingBy(MenuDto::getCategory));
    }

    //메뉴 상세 반환
    public MenuDto getMenuByRestaurantAndMenuId(Long restaurantId, Long menuId){
        List<Menu> menus = restaurantRepository.findMenusByRestaurantId(restaurantId);
        MenuDto menuDto = menus.stream()
                .filter(menu -> menu.getMenuId().equals(menuId))
                .findFirst()
                .map(menu -> MenuDto.builder()
                        .menuId(menu.getMenuId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .category(menu.getCategory())
                        .imgUrl(menu.getImgUrl())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
        return menuDto;
    }
}

