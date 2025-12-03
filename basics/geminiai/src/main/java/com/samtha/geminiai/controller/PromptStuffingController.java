package com.samtha.geminiai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prompt-stuffing")
public class PromptStuffingController {

    private final ChatClient chatClient;


    @Value("classpath:/promptTemplates/hrSystemPromptTemplate.st")
    Resource hrSystemPrompt;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/query")
    private String getPromptStuffing(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .system(hrSystemPrompt)
                .user(message)
                .call()
                .content();
    }
}
