package com.beerpub.order.service;

import com.beerpub.order.dao.Item;
import com.beerpub.order.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    Item getItemByID(Integer id);

    public List<Item> getAll();

    public ItemDTO addItem(String name, String description, Integer price, String category,
                           boolean availability, String picture, String chineseName);

    public ItemDTO deleteItem(String name);
}
