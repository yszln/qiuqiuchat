package com.yszln.qiuqiu.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yszln.qiuqiu.db.table.TbFriend

@Dao
interface FriendDao : BaseDao<TbFriend> {

    @Query("SELECT * FROM tb_friend ")
    fun findAll(): MutableList<TbFriend>

    @Query("DELETE FROM tb_friend")
    fun deleteAll()
}