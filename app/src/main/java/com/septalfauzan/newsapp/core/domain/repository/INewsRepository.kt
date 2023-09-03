package com.septalfauzan.newsapp.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.septalfauzan.newsapp.core.data.remote.response.NewsHeadlinestResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsListResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    suspend fun getNewsHeadline(category: String) : Flow<NewsHeadlinestResponse>
    fun getNews() : LiveData<PagingData<NewsRoomEntity>>
}