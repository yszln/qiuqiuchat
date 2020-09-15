package com.yszln.qiuqiu.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yszln.qiuqiu.MyApp
import com.yszln.qiuqiu.db.dao.ChatDao
import com.yszln.qiuqiu.db.dao.MessageDao
import com.yszln.qiuqiu.db.dao.UserDao
import com.yszln.qiuqiu.db.table.TbChat
import com.yszln.qiuqiu.db.table.TbMessage
import com.yszln.qiuqiu.db.table.TbUser

@Database(entities = [TbMessage::class, TbUser::class,TbChat::class], version =10)
abstract class CacheDataBase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun chatDao():ChatDao

    companion object {
        val instance = Single.instance
    }

    private object Single {

        val instance: CacheDataBase = Room.databaseBuilder(
            MyApp.instance,
            CacheDataBase::class.java,
            "cache.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}