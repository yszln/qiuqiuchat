package com.yszln.qiuqiu.controller;

import com.yszln.qiuqiu.bean.BaseBean;
import com.yszln.qiuqiu.bean.ErrorBean;
import com.yszln.qiuqiu.bean.SuccessBean;
import com.yszln.qiuqiu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @PostMapping("/upload")
    public BaseBean uploadFile(@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
        if(files==null||files.length==0){
            return new ErrorBean<>( "请选择需要上传的文件", null);
        }
        List<String> paths = fileService.uploadFile(files);
        if(null==paths){
            return new ErrorBean<>( "上传失败", null);
        }
        return new SuccessBean<>( "上传成功", paths);
    }


}
