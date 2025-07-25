package com.fork.app.domain.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "카카오 로그인 요청 DTO")
public class KakaoLoginDto {
    @Schema(description = "사용자 이름", example = "영호")
    private String nickname;
    @Schema(description = "사용자 이메일", example = "ajk6068@gmail.com")
    private String email;
}
