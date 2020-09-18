package com.yszln.qiuqiu.utils

import android.os.Environment
import com.yszln.lib.network.ApiFactory
import com.yszln.lib.utils.LogUtil
import com.yszln.qiuqiu.MyApp
import com.yszln.qiuqiu.api.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.*

fun String.subFileName(): String {
    return substring(lastIndexOf("/") + 1)
}

object DownloadUtils {

    val PATH =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MyApp.instance.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath + File.separator + "download"
        } else {
            File(Environment.getExternalStorageDirectory(), "download").absolutePath
        }

    init {
        if (!File(PATH).exists()) {
            File(PATH).mkdirs()
        }

    }

    suspend fun download(url: String): String {
        LogUtil.e("download start")
        var fileName = getFilePathName(url.subFileName())
        if(File(fileName).exists()){
            return fileName
        }
        GlobalScope.launch {
            val request: Request = Request.Builder()
                .url(url)
                .build()
            ApiFactory.mOkHttpClient.newCall(request).execute().body?.let {
                writeResponseBodyToDisk(it, fileName)
                LogUtil.e("download coroutine scope")
            }

        }.join()
        LogUtil.e("download end")
        return fileName

    }

    private fun getFilePathName(fileName: String) = PATH + File.separator + fileName

    private fun writeResponseBodyToDisk(body: ResponseBody, fileName: String): Boolean {
        return try {
            val futureStudioIconFile =
                File(fileName)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    LogUtil.e("file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

}