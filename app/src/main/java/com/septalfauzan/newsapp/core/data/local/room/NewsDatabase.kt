package com.septalfauzan.newsapp.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.septalfauzan.newsapp.core.data.local.entity.NewsRemoteKeys
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity

@Database(
    entities = [NewsRoomEntity::class, NewsRemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun newsRemoteKeysDao(): NewsRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "News.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
    }
}