package com.fork.app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "장바구니 응답 DTO")
public class CartResponseDto {
    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "장바구니 항목ID", example = "4")
    private Long cartItemId;

    @Schema(description = "메뉴 ID", example = "5")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "짬뽕")
    private String menuName;

    @Schema(description = "수량", example = "2")
    private int quantity;

    @Schema(description = "메뉴 단가", example = "9000")
    private int price;

    @Schema(description = "선택된 옵션 문자열 (예: '곱빼기, 치즈 추가')", example = "곱빼기, 치즈 추가")
    private String selectedOptions;
    //    private List<OptionDto> selectedOptions; // 옵션 리스트
    @Schema(description = "식당 ID", example = "20")
    private Long restaurantId;

    @Schema(description = "총 금액 (수량 × 가격 + 옵션 추가금)", example = "20000")
    private int totalPrice;
}
