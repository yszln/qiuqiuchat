package com.yszln.qiuqiu.ui.login.model

import com.yszln.qiuqiu.db.table.TbUser

data class LoginBean(val member: TbUser, val token: String)