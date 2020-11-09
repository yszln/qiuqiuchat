package com.yszln.qiuqiu.ui.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.qiuqiu.api.Api
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ChatViewModel : BaseViewModel() {
    val liveFiles = MutableLiveData<MutableList<String>>()

    fun upload(files: MutableList<File>) {

        launch(block = {
            val parts: MutableList<MultipartBody.Part> = ArrayList()

            for (file in files) {
                val requestBody: RequestBody = file.asRequestBody("audio/*".toMediaTypeOrNull())
                val form: MultipartBody.Part =
                    MultipartBody.Part.createFormData("files", file.name, requestBody)
                parts.add(form)
            }
            liveFiles.value = Api.mApiServer.uploadFiles(parts).data()
        })

    }
}