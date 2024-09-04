package com.beerpub.order.service;

import com.beerpub.order.dao.Item;
import com.beerpub.order.dao.ItemRepository;
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


}
