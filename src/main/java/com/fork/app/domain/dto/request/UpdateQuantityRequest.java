package com.fork.app.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "장바구니 수량 변경 요청 DTO")
public class UpdateQuantityRequest {
    @Schema(description = "변경할 수량", example = "3", required = true)
    private Integer quantity;
}