package com.fork.app.controller;

import com.fork.app.domain.dto.request.ChatbotRequestDto;
import com.fork.app.domain.entity.RestrictedFood;
import com.fork.app.domain.entity.User;
import com.fork.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "ChatBot API", description = "OpenAI 기반 챗봇 API")
@RequiredArgsConstructor
public class ChatBotController {
    private final RestTemplate restTemplate;
    private final UserService userService;
    @Operation(summary = "챗봇 응답", description = "사용자 메시지를 파이썬 서버로 전달 후 응답 반환")
    @PostMapping("/api/chatbot")
    public ResponseEntity<?> chat(@RequestBody ChatbotRequestDto chatbotRequest) {
        String pythonApiUrl = "http://localhost:8000/recommend";
        User user = userService.findById(chatbotRequest.getUserId());
        List<RestrictedFood> restrictedFoods = user.getRestrictedFoods();
        List<String> restrictions = new ArrayList<>();
        for (RestrictedFood restrictedFood : restrictedFoods) {
            restrictions.add(restrictedFood.getName());
        }

        Map<String, Object> request = new HashMap<>();
        request.put("message", chatbotRequest.getMessage()); // 사용자 메시지
        request.put("latitude", 37.5891);                    // 사용자 위치
        request.put("longitude", 127.0090);
        request.put("restrictions", List.of("치즈")); // 못 먹는 음식
        request.put("previousResults", List.of());
        ResponseEntity<Map> pythonResponse = restTemplate.postForEntity(pythonApiUrl, request, Map.class);
        return ResponseEntity.ok(pythonResponse.getBody());
    }
}
