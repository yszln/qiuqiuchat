package com.yszln.qiuqiu.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbUser

@Dao
interface UserDao : BaseDao<TbUser> {
    @Query("SELECT * FROM tb_user LIMIT 0,1")
    fun findFirst(): TbUser

    @Query("DELETE FROM tb_user")
    fun deleteAll()
}