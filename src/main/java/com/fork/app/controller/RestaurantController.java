package com.fork.app.controller;

import com.fork.app.domain.dto.request.MenuRequestDto;
import com.fork.app.domain.dto.request.RestaurantRequestDto;
import com.fork.app.domain.dto.response.MenuResponseDto;
import com.fork.app.domain.dto.response.RestaurantDetailResponseDto;
import com.fork.app.domain.dto.response.RestaurantListResponseDto;
import com.fork.app.service.MenuService;
import com.fork.app.service.RestaurantService;
import com.fork.app.service.S3UploaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "레스토랑 API", description = "카테고리별 식당 및 메뉴 조회")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final S3UploaderService s3UploaderService;
    private final MenuService menuService;

    // 1. 카테고리별 식당 조회
    @Operation(summary = "카테고리별 음식점 조회", description = "카테고리를 통해 해당 음식점 리스트를 조회합니다.")
    @GetMapping("/api/{categoryname}/restaurants")
    public ResponseEntity<?> getRestaurantsByCategory(@PathVariable String categoryname) {
        List<RestaurantListResponseDto> restaurantsByCategory = restaurantService.getRestaurantsByCategory(categoryname);
        return ResponseEntity.ok().body(restaurantsByCategory);
    }

    // 2. 특정 식당 상세 조회
    @Operation(summary = "음식점 상세 및 메뉴 목록 조회", description = "해당 음식점과 메뉴들을 카테고리별로 조회합니다.")
    @GetMapping("/api/restaurants/{restaurantId}")
    public ResponseEntity<?> getMenuList(@PathVariable Long restaurantId) {
        RestaurantDetailResponseDto restaurantDetail = restaurantService.getRestaurantDetail(restaurantId);
        return ResponseEntity.ok().body(restaurantDetail);
    }

    // 3. 메뉴 상세 조회
    @Operation(summary = "단일 메뉴 상세 조회", description = "음식점 ID와 메뉴 ID를 통해 특정 메뉴의 상세 정보를 조회합니다.")
    @GetMapping("/api/restaurants/{restaurantId}/menus/{menuId}")
    public ResponseEntity<?> getMenu(@PathVariable Long restaurantId, @PathVariable Long menuId) {
        MenuResponseDto menuResponseDto = restaurantService.getMenuByRestaurantAndMenuId(restaurantId, menuId);
        return ResponseEntity.ok().body(menuResponseDto);
    }

    // 4. 식당 + 메뉴 텍스트 등록 (1단계)
    @Operation(summary = "식당 및 메뉴 등록", description = "식당 및 메뉴를 JSON으로 등록합니다. 이미지 없이 텍스트만.")
    @PostMapping("/api/restaurant")
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequestDto requestDto) {
        restaurantService.createRestaurantWithMenus(requestDto);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "식당과 메뉴가 함께 등록되었습니다."
        ));
    }

    // 5. 식당 이미지 등록 (2단계)
    @Operation(summary = "식당 이미지 등록", description = "식당 ID에 대해 이미지 여러 개를 업로드합니다.")
    @PostMapping(value = "/api/restaurant/{restaurantId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertRestaurantImg(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestPart("restaurantImages") List<MultipartFile> restaurantImages
    ) {
        try {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : restaurantImages) {
                imageUrls.add(s3UploaderService.upload(file, "restaurant"));
            }

            restaurantService.updateRestaurantImages(restaurantId, imageUrls); // 추가 필요

            return ResponseEntity.ok(Map.of("result", "success", "message", "식당 이미지가 등록되었습니다."));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("result", "fail", "message", "이미지 업로드 실패"));
        }
    }

    // 6. 메뉴 이미지 등록 (2단계)
    @Operation(summary = "메뉴 이미지 등록", description = "메뉴 ID에 대해 단일 이미지 파일을 업로드합니다.")
    @PostMapping(value = "/api/menu/{menuId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertMenuImage(
            @PathVariable("menuId") Long menuId,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        try {
            String imageUrl = s3UploaderService.upload(imageFile, "menu");
            menuService.updateMenuImage(menuId, imageUrl); // 구현 필요
            return ResponseEntity.ok(Map.of("result", "success", "message", "메뉴 이미지가 등록되었습니다."));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("result", "fail", "message", "이미지 업로드 실패"));
        }
    }
}
