package com.fork.app.domain.dto;

import com.fork.app.domain.entity.RAddress;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuVectorizeDto {
    private Long id;               // 메뉴 ID
    private String menu;           // 메뉴명
    private String restaurant;     // 식당명
    private Long restaurantId;     // 식당 ID
    private RAddress address;      // 식당 주소
    private String category;       // 메뉴 카테고리
    private int price;             // 가격
    private boolean hasAR;         // AR 여부
    private boolean hasCoupon;         // AR 여부
}
