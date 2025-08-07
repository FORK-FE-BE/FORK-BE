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

    //새로 추가
    @Schema(description = "최소 주문 금액", example = "11000")
    private Integer minDeliveryPrice;

    @Schema(description = "최소 조리 시간", example = "15")
    private Integer minDeliveryTime;

    @Schema(description = "최대 조리 시간", example = "30")
    private Integer maxDeliveryTime;

    @Schema(description = "도/시", example = "서울특별시")
    private String province;

    @Schema(description = "구/군", example = "성북구")
    private String city;

    @Schema(description = "도로명", example = "보문로34길")
    private String roadName;

    @Schema(description = "건물 번호", example = "60")
    private String buildingNumber;

    @Schema(description = "한줄 소개", example = "최고의 맛을 위한 노력")
    private String introText;

    @Schema(description = "결제 방식", example = "앱 결제")
    private String paymentMethod;
}