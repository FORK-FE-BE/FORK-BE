package com.fork.app.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@Schema(description = "식당 리스트 응답 DTO")
public class RestaurantListResponseDto {

    @Schema(description = "식당 ID", example = "1")
    private Long id;

    @Schema(description = "식당 이름", example = "짬뽕지존 1호점")
    private String name;

    @Schema(description = "식당의 대표 메뉴 이미지 URL 리스트", example = "[\"https://example.com/menu1.jpg\", \"https://example.com/menu2.jpg\"]")
    private List<String> menus;

    @Schema(description = "식당 평점 (0.0 ~ 5.0)", example = "4.5")
    private BigDecimal rating;

    @Schema(description = "리뷰 수", example = "123")
    private int reviewCount;

    @Schema(description = "AR 기능 보유 여부", example = "true")
    private boolean hasAR;

    @Schema(description = "쿠폰 보유 여부", example = "false")
    private boolean hasCoupon;
}