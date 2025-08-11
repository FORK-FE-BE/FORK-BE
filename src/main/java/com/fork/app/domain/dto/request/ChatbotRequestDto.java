package com.fork.app.domain.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "chatbot.enabled", havingValue = "true")
@Data
@Schema(description = "챗봇 채팅 requestDTO", example = "스프링 ai에 대해서 간단하게 설명해줘")
public class ChatbotRequestDto {
    private Long userId;
    private String message;
}
