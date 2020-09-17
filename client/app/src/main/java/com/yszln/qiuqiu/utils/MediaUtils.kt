package com.yszln.qiuqiu.utils

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import com.yszln.lib.utils.LogUtil
import com.yszln.qiuqiu.MyApp
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

    @Throws(Exception::class)
    fun startRecord(): String {
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
    }

    @Throws(Exception::class)
    fun stopRecord() {
        media?.stop()
        media?.release()
        media = null
    }

    var mediaPlayer: MediaPlayer? = null

    @Throws(Exception::class)
    fun startPlaying(mediaPath: String) {
        stopPlaying()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(mediaPath)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    @Throws(Exception::class)
    fun stopPlaying() {
        mediaPlayer?.reset()
        mediaPlayer?.stop()
    }
}