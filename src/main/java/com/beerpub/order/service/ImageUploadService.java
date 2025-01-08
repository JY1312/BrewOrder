package com.beerpub.order.service;

import java.io.IOException;

public interface ImageUploadService {
    String uploadImage(String base64Image, String fileName) throws IOException;
}
