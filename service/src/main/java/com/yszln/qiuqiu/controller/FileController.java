package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.entity.BaseBean;
import com.yszln.qiuqiu.service.FileService;
import com.yszln.qiuqiu.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FileService fileService;

    @ResponseBody
    @PostMapping("/upload")
    public BaseBean uploadFile(@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
        List<String> paths = fileService.uploadFile(files);
        if(null==paths){
            return new BaseBean(500, "上传失败", null);
        }
        return new BaseBean(200, "上传成功", paths);
    }
}
