package com.beerpub.order.service;
import com.openai.client.OpenAIClient;

import com.openai.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {

    private final OpenAIClient client;

    public OpenAIService(OpenAIClient client) {
        this.client = client;
    }

    // create a chat completion without system message
    public ChatCompletion createChatCompletion(String message) {

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .messages(List.of(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(ChatCompletionUserMessageParam.builder()
                        .role(ChatCompletionUserMessageParam.Role.USER)
                        .content(ChatCompletionUserMessageParam.Content.ofTextContent(message))
                        .build())))
                .model(ChatModel.GPT_4O)
                .build();

        return client.chat().completions().create(params);
    }

    // create a chat completion with system message
    public ChatCompletion createChatCompletion(String systemMessage, String userMessage) {
        // 构造 ChatCompletionSystemMessageParam
        ChatCompletionSystemMessageParam systemMessageParam = ChatCompletionSystemMessageParam.builder()
                .role(ChatCompletionSystemMessageParam.Role.SYSTEM)
                .content(systemMessage) // 设置系统消息内容
                .build();

        // 构造 ChatCompletionUserMessageParam
        ChatCompletionUserMessageParam userMessageParam = ChatCompletionUserMessageParam.builder()
                .role(ChatCompletionUserMessageParam.Role.USER)
                .content(userMessage) // 设置用户消息内容
                .build();

        // 构造 ChatCompletionCreateParams
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .messages(List.of(
                        ChatCompletionMessageParam.ofChatCompletionSystemMessageParam(systemMessageParam), // system 消息
                        ChatCompletionMessageParam.ofChatCompletionUserMessageParam(userMessageParam)      // user 消息
                ))
                .model(ChatModel.GPT_4O) // 设置模型
                .build();

        // 调用 OpenAI API
        return client.chat().completions().create(params);
    }


    // generate a response for a message
    public String getResponse(String message) {
        ChatCompletion chatCompletion = createChatCompletion(message);

        // 如果 choices 不为空，直接返回第一个元素的内容
        return chatCompletion.choices().stream()
                .findFirst()
                .map(choice -> choice.message().content().orElse("default"))
                .orElse("default");
    }

    // generate a recommendation
    public String getResponse(String systemMessage, String userMessage) {
        ChatCompletion chatCompletion = createChatCompletion(systemMessage, userMessage);

        // 如果 choices 不为空，直接返回第一个元素的内容
        return chatCompletion.choices().stream()
                .findFirst()
                .map(choice -> choice.message().content().orElse("default"))
                .orElse("default");
    }
}
