package com.ssgsak.dudo.workRecommend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpenAIService {

    private final ChatClient chatClient;

    public OpenAIService(ChatClient chatClient) {
        this.chatClient = chatClient;

    }

    public String getChatGPTResponse(String systemMessage, String userMessage) {
        List<Message> messages = new ArrayList<>();

        messages.add(new SystemMessage(systemMessage));
        messages.add(new UserMessage(userMessage));

        ChatClient.CallResponseSpec call = chatClient
                .prompt()
                .messages(messages)
                .call();

        ChatResponse response = call.chatResponse();


        log.info("response.getMetadata().getModel() = {} ", response.getMetadata().getModel());
        log.info("response.getGenerationTokens() = {} ", response.getMetadata().getUsage().getGenerationTokens());
        log.info("response.getPromptTokens() = {} ", response.getMetadata().getUsage().getPromptTokens());
        log.info("response.getTotalTokens() = {} ", response.getMetadata().getUsage().getTotalTokens());


        return call.content();
    }
}

