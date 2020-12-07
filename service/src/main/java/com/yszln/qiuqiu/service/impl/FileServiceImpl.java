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
    public static final String PATH = "C://nginx//";
    public static final String UPLOAD_PATH = "upload";
    public static final String STATIC_HOST = "http://121.4.67.101/";


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
                            new File(PATH + UPLOAD_PATH + File.separator + fileName)
                    ));
                    stream.write(bytes);
                    stream.close();
                    paths.add(STATIC_HOST + UPLOAD_PATH + "/" + fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return paths;
    }
}
