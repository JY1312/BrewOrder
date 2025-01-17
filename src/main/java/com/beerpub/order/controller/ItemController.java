package com.beerpub.order.controller;

import com.beerpub.order.Response;
import com.beerpub.order.converter.ItemConverter;
import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;
import com.beerpub.order.service.ImageUploadService;
import com.beerpub.order.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/item/add")
    public Response<ItemDTO> addItem(@RequestBody AddItemRequest addItemRequest) {
        String originalFileName = addItemRequest.getItemData().getPicture();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            System.out.println("contained");
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println(fileExtension);
        }
        String newFilename = "items_" + addItemRequest.getItemData().getName() + fileExtension;

        try {
            imageUploadService.uploadImage(addItemRequest.getBase64Image(), newFilename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Response.newFail("Fail upload Image");
        }

        // 通过 ItemDTO 提供的字段调用服务方法
        try {
            ItemDTO result = itemService.addItem(
                    addItemRequest.getItemData().getName(),
                    addItemRequest.getItemData().getDescription(),
                    addItemRequest.getItemData().getPrice(),
                    addItemRequest.getItemData().getCategory(),
                    addItemRequest.getItemData().getAvailability(),
                    newFilename,
                    addItemRequest.getItemData().getChineseName(),
                    addItemRequest.getItemData().getBrewery(),
                    addItemRequest.getItemData().getStyle(),
                    addItemRequest.getItemData().getIbu(),
                    addItemRequest.getItemData().getAbv(),
                    addItemRequest.getItemData().getCapacity()
            );
            return Response.newSuccess(result);
        } catch (RuntimeException e) {
            return Response.newFail("添加Item失败 :(");
        }
    }

    @DeleteMapping("item/delete")
    public Response<ItemDTO> deleteItemById(@RequestParam Integer id){
        ItemDTO itemDTO = itemService.deleteItemById(id);
        if (itemDTO == null){
            return Response.newFail("item doesn't exist");
        }else {
            return Response.newSuccess(itemDTO);
        }
    }

    @PatchMapping("/item/update/{id}")
    public Response<ItemDTO> updateItemById(@PathVariable Integer id, @RequestBody ItemDTO itemDTO){
        ItemDTO updatedItemDTO = itemService.updateItemById(id, itemDTO);
        if (updatedItemDTO == null){
            return Response.newFail("item doesn't exist");
        }else{
            return Response.newSuccess(updatedItemDTO);
        }
    }

    @GetMapping("/item/get/{id}")
    public Response<ItemDTO> getItemById(@PathVariable Integer id) {
        try {
            // 调用 service 层方法，尝试获取数据
            Item item = itemService.getItemByID(id);
            // 如果成功获取数据，返回成功响应
            return Response.newSuccess(ItemConverter.convertItem(item));
        } catch (RuntimeException e) {
            return Response.newFail("Item with ID " + id + " doesn't exist");
        }
    }

    @GetMapping("/item/get/{name}")
    public Response<ItemDTO> getItemByName(@PathVariable String name) {
        try {
            // 调用 service 层方法，尝试获取数据
            ItemDTO itemDTO = itemService.getItemByName(name);
            // 如果成功获取数据，返回成功响应
            return Response.newSuccess(itemDTO);
        } catch (RuntimeException e) {
            return Response.newFail("Item with ID " + name + " doesn't exist");
        }
    }

    @GetMapping("item/get_all")
    public Response<List<ItemDTO>> getAllItem(){
        List<Item> itemList;
        try {
            itemList = itemService.getAll();
        } catch (RuntimeException e) {
            return Response.newFail("ERROR!");
        }
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for(Item item : itemList){
            itemDTOList.add(ItemConverter.convertItem(item));
        }
        return Response.newSuccess(itemDTOList);
    }

    @GetMapping("item/get_avail_drinks")
    public Response<List<ItemDTO>> getAvailDrinks(){
        List<ItemDTO> itemDTOList;
        try {
            itemDTOList = itemService.getAvailableDrinks();
        } catch (RuntimeException e) {
            return Response.newFail("ERROR!");
        }
        return Response.newSuccess(itemDTOList);
    }
}
