package com.yszln.qiuqiu.db.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: MutableList<T>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(t: T)

    @Delete
    fun delete(t: T)

    @Delete
    fun delete(ts: MutableList<T>)

    @Update
    fun update(t: T)


}