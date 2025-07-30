package com.fork.app.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuVectorizeDto {
    private Long id;               // 메뉴 ID
    private String menu;           // 메뉴명
    private String restaurant;     // 식당명
    private Long restaurantId;     // 식당 ID
    private String category;       // 메뉴 카테고리
    private int price;             // 가격
    private boolean hasAR;         // AR 여부
    private boolean hasCoupon;         // AR 여부
    private double latitude;       // 위도
    private double longitude;      // 경도
}
