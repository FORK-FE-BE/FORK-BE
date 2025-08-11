package com.fork.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//public class AppApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(AppApplication.class, args);
//    }
//
//}

@SpringBootApplication(
        exclude = {
                org.springframework.ai.model.openai.autoconfigure.OpenAiAudioTranscriptionAutoConfiguration.class,
                org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration.class,
                org.springframework.ai.model.openai.autoconfigure.OpenAiChatAutoConfiguration.class,  // Chat 기능 제외
                org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration.class // Embedding 기능 제외
        }
)
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}

