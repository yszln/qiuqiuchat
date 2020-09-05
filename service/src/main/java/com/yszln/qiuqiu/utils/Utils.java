package com.yszln.qiuqiu.utils;

public class Utils {

    /**
     * 获取文件后缀
     */
    public static String getFileSuffix(String fileOriginalName) {
       return fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
    }
}
