package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.dto.ItemDTO;
import com.beerpub.order.service.ItemService;
import com.beerpub.order.service.OpenAIService;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = {
                "http://localhost:3000",          // 本地开发环境
                "http://54.252.160.182",          // 服务器的公网 IP
                "http://mellowcb.com",            // HTTP 部署的域名
                "https://mellowcb.com",           // HTTPS 部署的域名
                "http://www.mellowcb.com",        // HTTP 的 www 子域名
                "https://www.mellowcb.com"        // HTTPS 的 www 子域名
        },
        allowCredentials = "true" // 允许携带 Cookie
)
public class AIController {
    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> handleChat(@RequestBody Map<String, String> payload) {
        String userInput = payload.get("userInput");

        // 调用 OpenAI API 获取回复
        String aiResponse = openAIService.getResponse(userInput);

        // 构建响应对象
        Map<String, String> response = Map.of(
                "input", userInput,
                "response", aiResponse
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/recommendation")
    public Response<ItemDTO> recommendBeer(@RequestBody Map<String, String> payload, HttpSession session) {
        String userInput = payload.get("userInput");

        List<String> recommendedBeers = new ArrayList<>();
        session.setAttribute("recommendedBeers", recommendedBeers);

        // 调用 OpenAI API 获取回复
        String systemMessage = getSystemMessage(recommendedBeers);
        String aiResponse = openAIService.getResponse(systemMessage, userInput);
        recommendedBeers.add(aiResponse);
        System.out.println("AI回复：" + aiResponse);

        // 构建响应对象
        ItemDTO itemDTO = itemService.getItemByName(aiResponse);

        if (itemDTO == null) {
            return Response.newFail("我恐怕有些不确定，请您咨询我们的服务员");
        }

        return Response.newSuccess(itemDTO);
    }

    @PostMapping("/recommendation-again")
    public Response<ItemDTO> recommendBeerAgain(
            @RequestBody Map<String, String> payload,
            HttpSession session
    ) {
        System.out.println(session.getId());
        String userInput = payload.get("userInput");
        System.out.println(userInput);

        @SuppressWarnings("unchecked")
        List<String> recommendedBeers =
                (List<String>) session.getAttribute("recommendedBeers");
        if (recommendedBeers == null) {
            recommendedBeers = new ArrayList<>();
            session.setAttribute("recommendedBeers", recommendedBeers);
        }


        String aiResponse = openAIService.getResponse(getSystemMessage(recommendedBeers) +
                "如果已经没有合适酒可以推荐，请回复mikulove", userInput);
        recommendedBeers.add(aiResponse);

        // 构建响应对象
        ItemDTO itemDTO = itemService.getItemByName(aiResponse);
        System.out.println(recommendedBeers);
        System.out.println(aiResponse);

        if (itemDTO == null) {
            return Response.newFail("‘我恐怕有些不确定，请您咨询我们的服务员’");
        }

        return Response.newSuccess(itemDTO);
    }


    @NotNull
    private String getSystemMessage(List<String> recommendedBeers) {
        List<ItemDTO> itemDTOS = itemService.getAvailableDrinks();
        StringBuilder beerString = new StringBuilder();
        System.out.println("总列表：");
        for (ItemDTO itemDTO : itemDTOS) {
            if (recommendedBeers.contains(itemDTO.getChineseName()) || recommendedBeers.contains(itemDTO.getName())) {
                System.out.println(itemDTO.getName());
                continue;
            }
            System.out.println(itemDTO.getName());
            beerString.append("Name: ").append(itemDTO.getName());
            beerString.append(", Description: ").append(itemDTO.getDescription());
            beerString.append(", Style: ").append(itemDTO.getStyle());
            beerString.append(", IBU: ").append(itemDTO.getIbu());
            beerString.append(", ABV: ").append(itemDTO.getAbv());
            beerString.append(", Capacity: ").append(itemDTO.getCapacity());
            beerString.append(", 中文名: ").append(itemDTO.getChineseName());
            beerString.append(", Price: ").append(itemDTO.getPrice());
        }
        return "你是一个酒吧服务员，我们的啤酒有: " + beerString + "接下来我会给你用户的需求，请你返回一款啤酒英文名字，注意只要啤酒名字！" +
                "请尽量推荐一款酒，除非用户骂你，你就说，请描述您想喝的酒。否则一定要尽量满足用户的需求！！！";
    }
}
