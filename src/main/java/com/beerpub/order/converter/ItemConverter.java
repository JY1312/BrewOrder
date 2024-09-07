package com.beerpub.order.converter;

import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;

public class ItemConverter {
    public static ItemDTO convertItem(Item item){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCategory(item.getCategory());
        itemDTO.setAvailability(item.getAvailability());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setChineseName(item.getChineseName());
        itemDTO.setName(item.getName());
        itemDTO.setItemId(item.getItemId());
        itemDTO.setPicture(item.getPicture());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    public static Item convertItemDTO(ItemDTO itemDTO){
        Item item = new Item();
        item.setAvailability(itemDTO.getAvailability());
        item.setCategory(itemDTO.getCategory());
        item.setDescription(itemDTO.getDescription());
        item.setChineseName(itemDTO.getChineseName());
        item.setName(itemDTO.getName());
        item.setPicture(itemDTO.getPicture());
        item.setPrice(itemDTO.getPrice());
        return item;
    }
}
