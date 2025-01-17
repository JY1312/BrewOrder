package com.beerpub.order;

import com.beerpub.order.service.OpenAIService;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class TestAPI {

    @Autowired
    private OpenAIService openAIService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestAPI.class, args);

        TestAPI testAPI = context.getBean(TestAPI.class);

        ChatCompletion chatCompletion = testAPI.openAIService.createChatCompletion("hi chat");

        for (ChatCompletion.Choice choice : chatCompletion.choices()) {
            ChatCompletionMessage msg = choice.message();

            String text = msg.content().orElse(""); // 这里才是真正的内容
            System.out.println("Content: " + text);
        }
    }
}
