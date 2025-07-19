package com.fork.app.domain.dto;

import lombok.Data;

@Data
public class CartRequestDto {
    private Long menuId;
    private int quantity;
    private String selectedOptions; // 예: 토핑/사이즈 등
}
