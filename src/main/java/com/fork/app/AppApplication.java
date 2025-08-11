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
                org.springframework.ai.model.openai.autoconfigure.OpenAiChatAutoConfiguration.class,
                org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration.class,
                org.springframework.ai.model.openai.autoconfigure.OpenAiImageAutoConfiguration.class,
                //org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration.class, // 혹시 Redis VectorStore 연동 방지
                //org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration.class // 혹시 pgvector 연동 방지
        }
)
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}

