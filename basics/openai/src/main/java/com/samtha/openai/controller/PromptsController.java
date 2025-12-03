package com.samtha.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prompts")
public class PromptsController {

    private final String DEFAULT_SYSTEM_PROMPT = """
            You are an internal HR assistant. Your role is to help \s
                        employees with questions related to HR policies such as  \s
                        leave policies, working hours, benefits and code of conduct. \s
                        If a user asks for help with anything outside of these topics, \s
                        kindly inform them that you can only assist with queries related to HR policies.
            """;
    private final String OVERIDDEN_SYSTEM_PROMPT = """
            You are an internal IT helpdesk assistant. Your role is to assist\s
            employees with IT-related issues such as resetting passwords,\s
            unlocking accounts, and answering questions related to IT policies.
            If a user requests help with anything outside of these\s
            responsibilities, respond politely and inform them that you are\s
            only able to assist with IT support tasks within your defined scope.
             """;

    private final ChatClient chatClient;

    public  PromptsController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.
                defaultSystem(DEFAULT_SYSTEM_PROMPT).
                defaultUser("How can you help me?").
                build();
    }


    @GetMapping("/hrchat")
    public String hrChat(@RequestParam("message") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/itchat")
    public String itChat(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(OVERIDDEN_SYSTEM_PROMPT)
                .user(message)
                .call()
                .content();
    }
    @GetMapping("/itchat/default")
    public String itChatDefaultUserMessage(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(OVERIDDEN_SYSTEM_PROMPT)
               // .user(message)
                .call()
                .content();
    }




}
