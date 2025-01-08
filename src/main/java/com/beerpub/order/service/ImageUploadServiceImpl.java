package com.beerpub.order.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String uploadImage(String base64Image, String fileName) throws IOException {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建目录
        }

        File destination = new File(uploadDir + fileName);

        // 将 Base64 解码为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // 将字节数组写入文件
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(imageBytes);
        }

        // 返回文件的访问路径
        return "/images/" + fileName;
    }
}