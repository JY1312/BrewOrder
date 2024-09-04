package com.beerpub.order.service;

import com.beerpub.order.dao.Item;

import java.util.List;

public interface ItemService {
    Item getItemByID(Integer id);

    public List<Item> getAll();
}
