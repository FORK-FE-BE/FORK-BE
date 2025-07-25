package com.fork.app.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "닉네임 수정 요청 DTO")
public class UpdateNicknameRequestDto {
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "새로운 닉네임", example = "맛집헌터")
    private String nickname;
}
