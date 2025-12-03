package com.samtha.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;
    private final String DEFAULT_SYSTEM_PROMPT = """
            You are an internal HR assistant. Your role is to help \s
                        employees with questions related to HR policies such as  \s
                        leave policies, working hours, benefits and code of conduct. \s
                        If a user asks for help with anything outside of these topics, \s
                        kindly inform them that you can only assist with queries related to HR policies.
            """;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt(message).call().content();
    }

    @GetMapping("/hrchat")
    public String hrChat(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(DEFAULT_SYSTEM_PROMPT)
                .user(message)
                .call()
                .content();
    }

}