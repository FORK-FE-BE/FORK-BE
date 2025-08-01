package com.fork.app.controller;

import com.fork.app.domain.dto.request.AllergyRequestDto;
import com.fork.app.domain.dto.request.KakaoLoginDto;
import com.fork.app.domain.dto.request.UpdateNicknameRequestDto;
import com.fork.app.domain.dto.response.UserInfoResponseDto;
import com.fork.app.domain.entity.User;
import com.fork.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "카카오 로그인 요청")
    @PostMapping("/api/login/kakao") //카카오 로그인
    public ResponseEntity<?> kakaoLoginController(@RequestBody KakaoLoginDto kakaoLoginDTO){
        log.info("카카오 로그인 요청: {}", kakaoLoginDTO.toString());
        boolean isExist = userService.isExistMember(kakaoLoginDTO.getEmail());
        UserInfoResponseDto responseDto = null;
        // ✅ 존재하지 않으면 회원가입 후 로그인 처리
        if(!isExist){
            responseDto = userService.registerMember(kakaoLoginDTO);
        }else{
            responseDto = userService.findByEmail(kakaoLoginDTO.getEmail());// 기존 유저 정보 가져오기
        }
        return ResponseEntity.ok().body(responseDto);
    }


    @Operation(summary = "사용자 정보 조회", description = "userId를 기반으로 유저 정보를 조회합니다.")
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserInfo(
            @Parameter(description = "유저 ID", example = "1")
            @RequestParam Long userId) {
        User user = userService.getUserInfo(userId);
        UserInfoResponseDto responseDto = user.entityToDto();

        return ResponseEntity.ok().body(responseDto);
    }

    @Operation(summary = "사용자 닉네임 수정", description = "userId를 기반으로 닉네임을 수정합니다.")
    @PutMapping("/api/user/profile/name")
    public ResponseEntity<?> modifyProfile(
            @RequestBody UpdateNicknameRequestDto requestDto){

        userService.updateNickname(requestDto.getUserId(), requestDto.getNickname());
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @Operation(summary = "사용자 알러지 목록 조회", description = "userId를 기반으로 알러지(못 먹는 음식) 목록을 조회합니다.")
    @GetMapping("/api/user/{userId}/allergies")
    public ResponseEntity<?> getAllergies(@PathVariable Long userId) {
        List<String> allergyList = userService.getAllergyByUserId(userId);
        return ResponseEntity.ok().body(allergyList);
    }

    @Operation(summary = "사용자 알러지 추가", description = "userId를 기반으로 알러지(못 먹는 음식)를 추가합니다.")
    @PostMapping("/api/user/{userId}/allergies/add")
    public ResponseEntity<?> addAllergy(@PathVariable Long userId, @RequestBody AllergyRequestDto requestDto) {
        userService.addAllergy(userId, requestDto.getAllergy());
        return ResponseEntity.ok().body(
                Map.of("result", "success")
        );
    }

//    @GetMapping("/api/users/reviews") //TODO 작성한 리뷰 조회
//    public ResponseEntity<?> getReviews(){
//        return ResponseEntity.ok(Map.of("result", "success"));
//    }

//    @GetMapping("/api/users/forkpoint") //TODO 포인트 조회
//    public ResponseEntity<?> getPoints(){
//        return ResponseEntity.ok(Map.of("result", "success"));
//    }
}
