package com.yszln.lib.utils

import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

/**
 * @author: yszln
 * @date: 2020/8/10 15:02
 * @description: 用来生成dp和sp文件
 * @history:
 */
fun main() {
    val dpFile = "./lib/src/main/res/values/dimens.xml"
    writeFile(dpFile, generateDPFile())
}

fun generateDPFile(): String {
    val dpSb = StringBuilder()
    dpSb.append(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>"
    ).append(" <dimen name =\"title_height\">45dp</dimen>")
    var dpItem = "";
    //生成dp文件
    for (i in 0..1000) {
        dpItem = "<dimen name=\"_${i}dp\">${i}dp</dimen>\n"
        dpSb.append(dpItem)
    }
    //生成sp文件
    for (i in 0..64) {
        dpItem = "<dimen name=\"_${i}sp\">${i}sp</dimen>\n"
        dpSb.append(dpItem)
    }
    dpSb.append("</resources>")
    return dpSb.toString()
}


fun writeFile(file: String?, text: String?) {
    var out: PrintWriter? = null
    try {
        out = PrintWriter(BufferedWriter(FileWriter(file)))
        out.println(text)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    out?.close()
}