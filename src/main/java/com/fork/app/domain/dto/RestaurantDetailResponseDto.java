package com.fork.app.domain.dto;


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
    private Long id;
    private String name;
    private BigDecimal rating;
    private int reviewCount;
    private String deliveryInfo;
    private List<String> storePictureUrl;
    private Map<String, List<MenuResponseDto>> menus;
}
