package com.fork.app.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "식당 추가 DTO")
public class RestaurantRequestDto {
    private String name;
    private String restaurantCategory;
    private RAddressRequestDto address;
    private Integer minDeliveryPrice;
    private Integer deliveryTip;
    private Integer minDeliveryTime;
    private Integer maxDeliveryTime;
    private BigDecimal rating;
    private Integer reviewCount;
    private Boolean hasAR;
    private Boolean hasCoupon;
    private List<String> storePictureUrl;
    private Map<String, List<MenuRequestDto>> menus; // 메뉴별 DTO 리스트


    private String fullRoadAddress; // 예: "서울특별시 성북구 종암로 10길 23"

}
