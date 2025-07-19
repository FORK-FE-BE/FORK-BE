package com.fork.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long userId;
    private Long cartItemId;
    private Long menuId;
    private String menuName;
    private int quantity;
    private int price;
    private String selectedOptions;
    private Long restaurantId;
    private int totalPrice;
}
