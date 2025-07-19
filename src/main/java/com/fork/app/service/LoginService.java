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
public class LoginService {
    private final UserRepository userRepository;
    public boolean isExistMember(String email) {
        return userRepository.findByEmail(email) != null;
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
}
