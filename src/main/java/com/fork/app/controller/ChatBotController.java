package com.fork.app.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatBotController {
    private final OpenAiChatModel openAiChatModel;

    public ChatBotController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @PostMapping("/api/chatbot")
    public ResponseEntity<?> chat(@RequestBody String message) {
        Map<String, String> responses = new HashMap<>();
        String openAiResponse = openAiChatModel.call(message);
        responses.put("response", openAiResponse);
        return ResponseEntity.ok().body(responses);
    }
}
