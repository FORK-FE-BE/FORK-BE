package com.fork.app.controller;

import com.fork.app.domain.dto.KakaoLoginDto;
import com.fork.app.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;

    @PostMapping("/api/login/kakao")
    public ResponseEntity<?> kakaoLoginController(@RequestBody KakaoLoginDto kakaoLoginDTO){
        log.info("카카오 로그인 요청: {}", kakaoLoginDTO.toString());
        boolean isExist = loginService.isExistMember(kakaoLoginDTO.getEmail());

        // ✅ 존재하지 않으면 회원가입 후 로그인 처리
        if(!isExist){
            loginService.registerMember(kakaoLoginDTO);
        }
        Map<String, String> token = loginService.generateToken(kakaoLoginDTO.getEmail());
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "accessToken", token.get("accessToken"),
                "refreshToken", token.get("refreshToken")
        ));
    }

    @PutMapping("/api/users/profileModify") //TODO 프로필 수정
    public ResponseEntity<?> modifyProfile(){
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping("/api/users/reviews") //TODO 작성한 리뷰 조회
    public ResponseEntity<?> getReviews(){
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping("/api/users/forkpoint") //TODO 포인트 조회
    public ResponseEntity<?> getPoints(){
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
