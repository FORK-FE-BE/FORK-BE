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
                org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration.class
        }
)
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
