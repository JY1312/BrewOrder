package com.beerpub.order.service;

import com.beerpub.order.converter.ItemConverter;
import com.beerpub.order.dao.Item;
import com.beerpub.order.dao.ItemRepository;
import com.beerpub.order.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                           boolean availability, String picture, String chineseName, String brewery, String style, Double ibu, Double abv, Integer capacity) {

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
        item.setBrewery(brewery);
        item.setStyle(style);
        item.setIbu(ibu);
        item.setAbv(abv);
        item.setCapacity(capacity);

        itemRepository.save(item);
        return ItemConverter.convertItem(item);
    }

    @Override
    public ItemDTO deleteItemById(Integer id) {
        Item item = itemRepository.getItemByItemId(id);
        if (item == null){
            return null;
        }else{
            itemRepository.delete(item);
            return ItemConverter.convertItem(item);
        }
    }

    @Override
    public ItemDTO updateItemById(Integer id, ItemDTO itemDTO) {
        Item item = getItemByID(id);
        if (item == null){
            return null;
        }

        if (itemDTO.getName() != null) {
            item.setName(itemDTO.getName());
        }
        if (itemDTO.getDescription() != null) {
            item.setDescription(itemDTO.getDescription());
        }
        if (itemDTO.getPrice() != null) {
            item.setPrice(itemDTO.getPrice());
        }
        if (itemDTO.getCategory() != null){
            item.setCategory(itemDTO.getCategory());
        }
        if (itemDTO.getAvailability() != null){
            item.setAvailability(itemDTO.getAvailability());
        }
        if (itemDTO.getPicture() != null){
            item.setPicture(itemDTO.getPicture());
        }
        if (itemDTO.getChineseName() != null){
            item.setChineseName(itemDTO.getChineseName());
        }
        return ItemConverter.convertItem(itemRepository.save(item));
    }

    @Override
    public ItemDTO getItemByName(String name) {
        Item item = itemRepository.getItemByName(name);
        if (item == null){
            return null;
        }  else {
            return ItemConverter.convertItem(item);
        }
    }

    @Override
    public List<ItemDTO> getAvailableDrinks() {
        return itemRepository.findByAvailabilityTrueAndCategory("drink")
                .stream()
                .map(ItemConverter::convertItem) // 使用转换器将 Item 转换为 ItemDTO
                .collect(Collectors.toList()); // 收集转换后的结果
    }

}
