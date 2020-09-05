package com.yszln.qiuqiu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<String> uploadFile(MultipartFile files[]);
}
