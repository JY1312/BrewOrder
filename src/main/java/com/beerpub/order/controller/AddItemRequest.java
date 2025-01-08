package com.beerpub.order.controller;

import com.beerpub.order.dto.ItemDTO;

public class AddItemRequest {
    private ItemDTO itemData;  // 商品数据
    private String base64Image; // 图片的 Base64 编码

    // Getters and Setters

    public ItemDTO getItemData() {
        return itemData;
    }

    public void setItemData(ItemDTO itemData) {
        this.itemData = itemData;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
