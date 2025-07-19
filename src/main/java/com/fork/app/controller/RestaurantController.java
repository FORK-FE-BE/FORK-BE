package com.fork.app.controller;

import com.fork.app.domain.dto.MenuDto;
import com.fork.app.domain.dto.RestaurantDto;
import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/api/{categoryname}/restaurants") //카테고리 클릭시 음식점 리스트 조회
    public ResponseEntity<?> getRestaurantsByCategory(@PathVariable String categoryname) {
        // categoryname 값 이용 가능
        log.info("controller 시작");
        List<RestaurantDto> restaurantsByCategory = restaurantService.getRestaurantsByCategory(categoryname);
        log.info("카테고리 선택: {}", categoryname);
        return ResponseEntity.ok().body(restaurantsByCategory);
    }

    @GetMapping("/api/restaurants/{restaurantId}") //해당 음식점조회/메뉴리스트 조회
    public ResponseEntity<?> getMenuList(
            @PathVariable Long restaurantId) {
        Map<MenuCategoryEnum, List<MenuDto>> menusOfRestaurant = restaurantService.getMenusOfRestaurant(restaurantId);
        log.info("식당 조회 -> 식당메뉴들: {}", menusOfRestaurant);
        return ResponseEntity.ok().body(menusOfRestaurant);
    }

    @GetMapping("/api/restaurants/{restaurantId}/menus/{menuId}")
    public ResponseEntity<?> getMenu(
            @PathVariable Long restaurantId,
            @PathVariable Long menuId) {
        log.info("{}번식당 메뉴조회", restaurantId);
        MenuDto menuDto = restaurantService.getMenuByRestaurantAndMenuId(restaurantId, menuId);

        return ResponseEntity.ok().body(menuDto);
    }
}
