package com.beerpub.order;


import com.beerpub.order.dao.Item;
import com.beerpub.order.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ItemService itemService;
    @GetMapping("/hello")
    public String hello(){
        return "hello spring!";
    }


    @GetMapping("/allItems")
    public List<Item> showAllItems(){
        return itemService.getAll();
    }


}
