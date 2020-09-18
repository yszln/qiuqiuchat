package com.yszln.qiuqiu.api

import com.yszln.lib.bean.BaseBean
import com.yszln.qiuqiu.db.table.TbFriend
import com.yszln.qiuqiu.ui.login.model.LoginBean
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
 */
@JvmSuppressWildcards
interface ApiServer {

    /**
     * 登录
     */
    @POST("/member/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseBean<LoginBean>

    /**
     * 注册
     */
    @POST("/member/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseBean<LoginBean>

    /**
     * 查询所有好友
     */
    @POST("/friend/findAll")
    suspend fun getFriends(): BaseBean<MutableList<TbFriend>>

    /**
     * 上传文件，支持多文件上传
     * @param files
     */
    @POST("/file/upload")
    @Multipart
    suspend fun uploadFiles(@Part files: MutableList<MultipartBody.Part>): BaseBean<MutableList<String>>


}