package com.beerpub.order.converter;

import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;

public class ItemConverter {
    public static ItemDTO convertItem(Item item){
        if(item == null){ return null; }
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCategory(item.getCategory());
        itemDTO.setAvailability(item.getAvailability());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setChineseName(item.getChineseName());
        itemDTO.setName(item.getName());
        itemDTO.setItemId(item.getItemId());
        itemDTO.setPicture(item.getPicture());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setAbv(item.getAbv());
        itemDTO.setIbu(item.getIbu());
        itemDTO.setStyle(item.getStyle());
        itemDTO.setBrewery(item.getBrewery());
        itemDTO.setCapacity(item.getCapacity());
        return itemDTO;
    }

    public static Item convertItemDTO(ItemDTO itemDTO){
        if(itemDTO == null){ return null; }
        Item item = new Item();
        item.setAvailability(itemDTO.getAvailability());
        item.setCategory(itemDTO.getCategory());
        item.setDescription(itemDTO.getDescription());
        item.setChineseName(itemDTO.getChineseName());
        item.setName(itemDTO.getName());
        item.setPicture(itemDTO.getPicture());
        item.setPrice(itemDTO.getPrice());
        item.setAbv(itemDTO.getAbv());
        item.setIbu(itemDTO.getIbu());
        item.setStyle(itemDTO.getStyle());
        item.setBrewery(itemDTO.getBrewery());
        item.setCapacity(itemDTO.getCapacity());
        return item;
    }
}
