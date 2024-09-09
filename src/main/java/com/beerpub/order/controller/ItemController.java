package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.converter.ItemConverter;
import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;
import com.beerpub.order.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public Response deleteItemById(@RequestParam Integer id){
        ItemDTO itemDTO = itemService.deleteItemById(id);
        if (itemDTO == null){
            return Response.newFail("item doesn't exist");
        }else {
            return Response.newSuccess(itemDTO);
        }
    }

    @PatchMapping("/item/update/{id}")
    public Response updateItemById(@PathVariable Integer id, @RequestBody ItemDTO itemDTO){
        ItemDTO updatedItemDTO = itemService.updateItemById(id, itemDTO);
        if (updatedItemDTO == null){
            return Response.newFail("item doesn't exist");
        }else{
            return Response.newSuccess(updatedItemDTO);
        }
    }

    @GetMapping("/item/get/{id}")
    public Response getItemById(@PathVariable Integer id){
        Item item = itemService.getItemByID(id);
        if (item == null){
            return Response.newFail("item doesn't exist");
        }else{
            return Response.newSuccess(ItemConverter.convertItem(item));
        }
    }

    @GetMapping("item/get_all")
    public Response getAllItem(){
        List<Item> itemList= itemService.getAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for(Item item : itemList){
            itemDTOList.add(ItemConverter.convertItem(item));
        }
        return Response.newSuccess(itemDTOList);
    }

}
