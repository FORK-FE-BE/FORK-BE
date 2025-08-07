package com.fork.app.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    @Schema(description = "장바구니 항목ID", example = "4")
    private Long cartItemId;

    @Schema(description = "메뉴 ID", example = "5")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "짬뽕")
    private String menuName;

    @Schema(description = "수량", example = "2")
    private int quantity;

    @Schema(description = "메뉴 단가", example = "2000")
    private int price;

    @Schema(description = "메뉴 단가", example = "9000")
    private int totalPrice;

    @Schema(description = "선택한 옵션 정보 (옵션 그룹 이름, 옵션 이름, 옵션 가격)")
    private List<Map<String, Object>> options;

}
