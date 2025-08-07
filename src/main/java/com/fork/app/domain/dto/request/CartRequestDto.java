package com.fork.app.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "장바구니 요청 DTO")
public class CartRequestDto {
    @Schema(description = "메뉴 ID", example = "3")
    private Long menuId;

    @Schema(description = "수량", example = "2")
    private int quantity;

    @Schema(description = "선택한 옵션 목록")
    private List<CartOptionDto> selectedOptions;
}
