package com.beerpub.order.service;

import com.beerpub.order.converter.ItemConverter;
import com.beerpub.order.dao.Item;
import com.beerpub.order.dao.ItemRepository;
import com.beerpub.order.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;
    @Override
    public Item getItemByID(Integer id) {
        return itemRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    @Override
    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    @Override
    public ItemDTO addItem(String name, String description, Integer price, String category,
                           boolean availability, String picture, String chineseName) {
        if (itemRepository.getItemByName(name) != null){
            return null;
        }
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setAvailability(availability);
        item.setPicture(picture);
        item.setChineseName(chineseName);

        itemRepository.save(item);
        return ItemConverter.convertItem(item);
    }


}
