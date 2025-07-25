package com.fork.app.controller;

import com.fork.app.domain.dto.request.ChatbotRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ChatBotController {
    private final OpenAiChatModel openAiChatModel;

    public ChatBotController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @PostMapping("/api/chatbot")
    public ResponseEntity<?> chat(@RequestBody ChatbotRequestDto chatbotRequest) {
        log.info("챗봇 컨트롤러 진입");
        Map<String, String> responses = new HashMap<>();
        String openAiResponse = openAiChatModel.call(chatbotRequest.getMessage());
        responses.put("response", openAiResponse);
        return ResponseEntity.ok().body(responses);
    }
}
