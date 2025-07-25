package com.fork.app.domain.dto.response;

import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Data
@Schema(description = "식당 응답 DTO")
public class RestaurantResponseDto {
    @Schema(description = "식당 ID", example = "1")
    private Long restaurantId;

    @Schema(description = "식당 이름", example = "짬뽕지존 1호점")
    private String name;

    @Schema(description = "식당 카테고리", example = "중식")
    private RestaurantCategoryEnum restaurantCategoryEnum;

    @Schema(description = "식당 주소", example = "서울특별시 강남구 역삼동 123-45")
    private String address;

    @Schema(description = "식당 메뉴 목록")
    private List<MenuResponseDto> menus;

    @Schema(description = "식당 대표 이미지 URL 목록", example = "[\"https://fork-app.s3/.../store1.jpg\", \"https://fork-app.s3/.../store2.jpg\"]")
    private List<String> storePictureUrl;

    @Schema(description = "식당 전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "식당 평점", example = "4.5")
    private BigDecimal rating;

    @Schema(description = "리뷰 수", example = "345")
    private Integer reviewCount;

    @Schema(description = "등록일", example = "2025-07-18T13:30:00")
    private LocalDateTime createdDate;
//    private String content;
//    private Integer minDeliveryPrice;
//    private Integer deliveryTip;
//    private Integer minDeliveryTime;
//    private Integer maxDeliveryTime;
}
