package com.yszln.qiuqiu.api

import com.yszln.lib.bean.BaseBean
import com.yszln.qiuqiu.db.table.TbUser
import com.yszln.qiuqiu.ui.login.model.LoginBean
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

    @POST("/friend/findAll")
    suspend fun getFriends(): BaseBean<MutableList<TbUser>>

}