package com.yszln.qiuqiu.service.impl;

import com.yszln.qiuqiu.service.FileService;
import com.yszln.qiuqiu.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    //nginx的路径
    public static final String PATH = "E:\\soft\\nginx-1.15.11\\html\\";
    public static final String UPLOAD_PATH = "upload" + File.separator;


    @Override
    public List<String> uploadFile(MultipartFile[] files) {
        List<String> paths = new ArrayList<>();
        BufferedOutputStream stream;
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = System.currentTimeMillis() + file.hashCode() + Utils.getFileSuffix(file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(PATH + UPLOAD_PATH + fileName)
                    ));
                    stream.write(bytes);
                    stream.close();
                    paths.add(UPLOAD_PATH + fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return paths;
    }
}
