package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.converter.ItemConverter;
import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;
import com.beerpub.order.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/item/add")
    public Response addItem(@RequestParam String name, @RequestParam String description,
                                     @RequestParam Integer price, @RequestParam String category,
                                     @RequestParam boolean availability, @RequestParam String picture,
                                     @RequestParam String chineseName){
        ItemDTO itemDTO = itemService.addItem(name, description, price, category, availability, picture, chineseName);
        if (itemDTO == null){
             return Response.newFail("item already exist");
         }else{
             return Response.newSuccess(itemDTO);
         }
    }

    @DeleteMapping("item/delete")
    public Response deleteItem(@RequestParam String name){
        ItemDTO itemDTO = itemService.deleteItem(name);
        if (itemDTO == null){
            return Response.newFail("item doesn't exist");
        }else {
            return Response.newSuccess(itemDTO);
        }
    }

    @PatchMapping("/item/update/{id}")
    public Response updateItem(@PathVariable Integer id, @RequestBody ItemDTO itemDTO){
        ItemDTO updatedItemDTO = itemService.updateItem(id, itemDTO);
        if (updatedItemDTO == null){
            return Response.newFail("item doesn't exist");
        }else{
            return Response.newSuccess(updatedItemDTO);
        }
    }

    @GetMapping("/item/get/{id}")
    public Response getItem(@PathVariable Integer id){
        Item item = itemService.getItemByID(id);
        if (item == null){
            return Response.newFail("item doesn't exist");
        }else{
            return Response.newSuccess(ItemConverter.convertItem(item));
        }
    }

}
