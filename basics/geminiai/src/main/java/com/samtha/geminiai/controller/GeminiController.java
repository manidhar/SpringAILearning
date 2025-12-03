package com.samtha.geminiai.controller;




import com.google.genai.Chat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GeminiController {

    private final ChatClient chatClient;
    private final String OVERIDDEN_SYSTEM_PROMPT = """
            You are an internal IT helpdesk assistant. Your role is to assist\s
            employees with IT-related issues such as resetting passwords,\s
            unlocking accounts, and answering questions related to IT policies.
            If a user requests help with anything outside of these\s
            responsibilities, respond politely and inform them that you are\s
            only able to assist with IT support tasks within your defined scope.
             """;

    public GeminiController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/gemini")
    public String getChatResponse(@RequestParam("message") String message){
        return chatClient.prompt(message)
                .system(OVERIDDEN_SYSTEM_PROMPT)
                .user(message)
                .call()
                .content();
    }

}
