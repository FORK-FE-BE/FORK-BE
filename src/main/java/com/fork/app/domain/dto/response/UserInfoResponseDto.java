package com.fork.app.domain.dto.response;

import com.fork.app.domain.entity.enumtype.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "유저 정보 응답 DTO")
public class UserInfoResponseDto {
    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "hong@example.com")
    private String email;

    @Schema(description = "권한", example = "USER")
    private MemberRole role;

    @Schema(description = "가입일", example = "2025-07-19T12:00:00")
    private LocalDateTime createdDate;
}
