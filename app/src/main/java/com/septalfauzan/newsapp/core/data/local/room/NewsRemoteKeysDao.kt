package com.septalfauzan.newsapp.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.septalfauzan.newsapp.core.data.local.entity.NewsRemoteKeys

@Dao
interface NewsRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<NewsRemoteKeys>)

    @Query("SELECT * FROM news_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): NewsRemoteKeys?

    @Query("DELETE FROM news_remote_keys")
    suspend fun deleteRemoteKeys()
}