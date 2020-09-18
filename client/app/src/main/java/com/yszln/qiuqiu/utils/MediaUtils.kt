package com.yszln.qiuqiu.utils

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.text.TextUtils
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.utils.toast
import com.yszln.qiuqiu.MyApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


object MediaUtils {

    var media: MediaRecorder? = null

    private val OUTPUT_PATH =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MyApp.instance.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath + File.separator + "voice"
        } else {
            File(Environment.getExternalStorageDirectory(), "voice").absolutePath
        }

    init {
        if (!File(OUTPUT_PATH).exists()) {
            File(OUTPUT_PATH).mkdirs()
        }

    }

    fun startRecord(): String {
        try {
            stopRecord()
            media = MediaRecorder()
            media?.setAudioSource(MediaRecorder.AudioSource.MIC)
            media?.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB)
            media?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)

            val recorder = File(OUTPUT_PATH + File.separator + System.currentTimeMillis() + ".amr")
            LogUtil.e("outFile:${recorder.absolutePath}")
            recorder.createNewFile()
            media?.setOutputFile(recorder.absolutePath)
            media?.prepare()
            media?.start()
            return recorder.absolutePath
        } catch (e: Exception) {
            "录制失败:${e.message}".toast()
        }
        return ""


    }

    fun stopRecord() {
        media?.stop()
        media?.release()
        media = null
    }

    var mediaPlayer: MediaPlayer? = null

    fun startPlaying(mediaPath: String?) {
        var mediaPath = mediaPath ?: ""
        if (TextUtils.isEmpty(mediaPath)) return

        stopPlaying()
        mediaPlayer = MediaPlayer()
        try {
            GlobalScope.launch {
                if (mediaPath.startsWith("http")) {
                    mediaPath = DownloadUtils.download(mediaPath)
                }
                LogUtil.e("dataSource:${mediaPath}")
                mediaPlayer?.setDataSource(mediaPath)


                mediaPlayer?.prepare()
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            "播放失败:${e.message}".toast()
        }


    }

    fun stopPlaying() {
        mediaPlayer?.stop()
        mediaPlayer = null
    }
}