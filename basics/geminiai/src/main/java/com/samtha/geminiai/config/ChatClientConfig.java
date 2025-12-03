package com.samtha.geminiai.config;

import com.samtha.geminiai.advisors.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatClientConfig {
    private final String DEFAULT_SYSTEM_PROMPT = """
            You are an internal HR assistant. Your role is to help \s
                        employees with questions related to HR policies such as  \s
                        leave policies, working hours, benefits and code of conduct. \s
                        If a user asks for help with anything outside of these topics, \s
                        kindly inform them that you can only assist with queries related to HR policies.
            """;
    @Bean
    @Primary
    public ChatClient getChatClient(ChatClient.Builder chatClientBuilder) {

        ChatOptions options=ChatOptions.builder()
                .model("gemini-2.0-flash")
                .temperature(0.8)
                //.maxTokens(1000)
                .build();

        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAdvisor()))
                .defaultOptions(options)
                .defaultSystem(DEFAULT_SYSTEM_PROMPT)
                .defaultUser("How can you help me?")
                .build();
    }

}
