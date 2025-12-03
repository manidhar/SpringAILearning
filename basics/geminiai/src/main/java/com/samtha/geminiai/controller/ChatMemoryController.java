package com.samtha.geminiai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

private final ChatClient chatClient;

public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatClient){
    this.chatClient=chatClient;
}

@GetMapping("/memory-convId")
public String chatMemory(@RequestHeader("userName") String userName, @RequestParam("message") String message){
    return chatClient
            .prompt()
            .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,userName))
            .user(message)
            .call()
            .content();
}

    @GetMapping("/memory")
    public String chatMemoryWithConversationId(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }

}
