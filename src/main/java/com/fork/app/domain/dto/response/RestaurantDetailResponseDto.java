package com.fork.app.domain.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Schema(description = "음식점 상세 응답 DTO")
public class RestaurantDetailResponseDto {

    @Schema(description = "식당 ID", example = "1")
    private Long id;

    @Schema(description = "식당 이름", example = "짬뽕지존 1호점")
    private String name;

    @Schema(description = "식당 평점", example = "4.5")
    private BigDecimal rating;

    @Schema(description = "리뷰 수", example = "120")
    private int reviewCount;

    @Schema(description = "배달 정보 요약", example = "최소주문 12,000원 / 배달팁 2,000원")
    private String deliveryInfo;

    @Schema(description = "식당 이미지 리스트", example = "[\"https://example.com/store1.jpg\"]")
    private List<String> storePictureUrl;

    @Schema(description = "카테고리별 메뉴", example = "{ \"대표메뉴\": [ ... ], \"사이드\": [ ... ] }")
    private Map<String, List<MenuResponseDto>> menus;
}