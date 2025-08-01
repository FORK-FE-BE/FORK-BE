package com.fork.app.service;


import com.fork.app.domain.dto.response.AllergyResponseDto;
import com.fork.app.domain.dto.request.AllergyMenu;
import com.fork.app.domain.dto.request.KakaoLoginDto;
import com.fork.app.domain.dto.response.UserInfoResponseDto;
import com.fork.app.domain.entity.RestrictedFood;
import com.fork.app.domain.entity.User;
import com.fork.app.domain.entity.enumtype.MemberRole;
import com.fork.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public boolean isExistMember(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void updateNickname(Long userId, String nickname){
        User user = userRepository.findById(userId).orElseThrow(() ->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.changeNickname(nickname);
    }

    public Map<String, String> generateToken(String email) {
        //TODO 토큰생성 로직 구현
        return Map.of("refreshToken", "refreshToken",
                "accessToken", "accessToken"
        );
    }


    public UserInfoResponseDto registerMember(KakaoLoginDto kakaoLoginDTO) {
        User user = User.builder()
                .role(MemberRole.USER)
                .createdDate(LocalDateTime.now())
                .email(kakaoLoginDTO.getEmail())
                .name(kakaoLoginDTO.getNickname())
                .build();
        userRepository.save(user);

        UserInfoResponseDto userDto = UserInfoResponseDto.builder()
                .userId(user.getUserId())
                .createdDate(user.getCreatedDate())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .build();
        return userDto;
    }

    public User getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return user;
    }

    public UserInfoResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserInfoResponseDto userDto = UserInfoResponseDto.builder()
                .userId(user.getUserId())
                .createdDate(user.getCreatedDate())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .build();
        return userDto;
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
    public List<String> getAllergyByUserId(Long userId) {
        User user = findById(userId);
        return user.getRestrictedFoods().stream()
                .map(RestrictedFood::getName)
                .filter(Objects::nonNull) // name이 null인 항목 제외
                .toList();
    }

    public void addAllergy(Long userId, List<String> allergy) {
        User user = findById(userId);
        // 새로 설정할 알러지 리스트 생성
        List<RestrictedFood> allergyList = allergy.stream()
                .map(menu -> RestrictedFood.builder()
                        .name(menu)
                        .user(user)
                        .build())
                .toList();

        // 기존 알러지 제거 후 새로 추가
        user.getRestrictedFoods().clear();
        user.getRestrictedFoods().addAll(allergyList);
    }
}
