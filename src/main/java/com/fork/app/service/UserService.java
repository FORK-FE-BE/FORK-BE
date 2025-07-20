package com.fork.app.service;


import com.fork.app.domain.dto.KakaoLoginDto;
import com.fork.app.domain.entity.User;
import com.fork.app.domain.entity.enumtype.MemberRole;
import com.fork.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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


    public void registerMember(KakaoLoginDto kakaoLoginDTO) {
        User user = User.builder()
                .role(MemberRole.USER)
                .createdDate(LocalDateTime.now())
                .email(kakaoLoginDTO.getEmail())
                .name(kakaoLoginDTO.getNickname())
                .build();
        userRepository.save(user);
    }

    public User getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return user;
    }
}
