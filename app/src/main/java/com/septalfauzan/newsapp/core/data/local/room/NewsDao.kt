package com.septalfauzan.newsapp.core.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: List<NewsRoomEntity>)

    @Query("SELECT * FROM news")
    fun getNews(): PagingSource<Int, NewsRoomEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}