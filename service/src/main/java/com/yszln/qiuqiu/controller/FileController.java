package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.utils.Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    //nginx的路径
    public static final String PATH = "E:\\soft\\nginx-1.15.11\\html\\";
    public static final String UPLOAD_PATH = "upload\\";

    @ResponseBody
    @PostMapping("/upload")
    public BaseBean uploadFile(@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
        List<String> paths = new ArrayList<>();
        BufferedOutputStream stream;
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileName = System.currentTimeMillis() + file.hashCode() + Utils.getFileSuffix(file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(PATH +UPLOAD_PATH+ fileName)
                    ));
                    stream.write(bytes);
                    stream.close();
                    paths.add(UPLOAD_PATH+fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    return new BaseBean(-1, "上传失败："+e.getMessage(),null);
                }
            }
        }
        return new BaseBean(0, "上传成功", paths);
    }
}
