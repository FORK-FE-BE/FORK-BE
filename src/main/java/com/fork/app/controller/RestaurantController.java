package com.fork.app.controller;

import com.fork.app.domain.dto.MenuResponseDto;
import com.fork.app.domain.dto.RestaurantResponseDto;
import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "레스토랑 API", description = "카테고리별 식당 및 메뉴 조회")
public class RestaurantController {
    private final RestaurantService restaurantService;


//    @Operation(summary = "카테고리별 음식점 조회", description = "카테고리를 통해 해당 음식점 리스트를 조회합니다.")
//    @GetMapping("/api/{categoryname}/restaurants") //카테고리 클릭시 음식점 리스트 조회
//    public ResponseEntity<?> getRestaurantsByCategory(
//            @Parameter(description = "음식 카테고리 이름 (예: 중식, 디저트, 치킨)", example = "중식")
//            @PathVariable String categoryname) {
//        // categoryname 값 이용 가능
//        log.info("controller 시작");
//        List<RestaurantResponseDto> restaurantsByCategory = restaurantService.getRestaurantsByCategory(categoryname);
//        log.info("카테고리 선택: {}", categoryname);
//        return ResponseEntity.ok().body(restaurantsByCategory);
//    }

    @Operation(summary = "카테고리별 음식점 조회", description = "카테고리를 통해 해당 음식점 리스트를 조회합니다.")
    @GetMapping("/api/{categoryname}/restaurants") //카테고리 클릭시 음식점 리스트 조회
    public ResponseEntity<?> getRestaurantsByCategory(
            @Parameter(description = "음식 카테고리 이름 (예: 중식, 디저트, 치킨)", example = "중식")
            @PathVariable String categoryname) {
        // categoryname 값 이용 가능
        log.info("controller 시작");
        List<RestaurantResponseDto> restaurantsByCategory = restaurantService.getRestaurantsByCategory(categoryname);
        log.info("카테고리 선택: {}", categoryname);
        return ResponseEntity.ok().body(restaurantsByCategory);
    }

    @Operation(summary = "음식점 상세 및 메뉴 목록 조회", description = "해당 음식점의 메뉴들을 카테고리별로 조회합니다.")
    @GetMapping("/api/restaurants/{restaurantId}") //해당 음식점조회/메뉴리스트 조회
    public ResponseEntity<?> getMenuList(
            @Parameter(description = "음식점 ID", example = "1")
            @PathVariable Long restaurantId) {
        Map<MenuCategoryEnum, List<MenuResponseDto>> menusOfRestaurant = restaurantService.getMenusOfRestaurant(restaurantId);
        log.info("식당 조회 -> 식당메뉴들: {}", menusOfRestaurant);
        return ResponseEntity.ok().body(menusOfRestaurant);
    }

    @Operation(summary = "단일 메뉴 상세 조회", description = "음식점 ID와 메뉴 ID를 통해 특정 메뉴의 상세 정보를 조회합니다.")
    @GetMapping("/api/restaurants/{restaurantId}/menus/{menuId}")
    public ResponseEntity<?> getMenu(
            @Parameter(description = "음식점 ID", example = "1")
            @PathVariable Long restaurantId,
            @Parameter(description = "메뉴 ID", example = "1")
            @PathVariable Long menuId) {
        log.info("{}번식당 메뉴조회", restaurantId);
        MenuResponseDto menuResponseDto = restaurantService.getMenuByRestaurantAndMenuId(restaurantId, menuId);

        return ResponseEntity.ok().body(menuResponseDto);
    }
}
