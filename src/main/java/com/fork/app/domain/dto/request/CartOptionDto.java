package com.fork.app.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "장바구니 옵션 DTO")
public class CartOptionDto {

    @Schema(description = "옵션 그룹명", example = "토핑 추가")
    private String groupName;

    @Schema(description = "옵션 이름", example = "치즈 추가")
    private String optionName;

    @Schema(description = "옵션 가격", example = "1500")
    private int optionPrice;
}
