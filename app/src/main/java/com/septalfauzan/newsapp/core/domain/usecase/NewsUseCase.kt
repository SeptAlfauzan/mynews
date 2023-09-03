package com.septalfauzan.newsapp.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.septalfauzan.newsapp.core.data.remote.response.NewsHeadlinestResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsListResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import com.septalfauzan.newsapp.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsUseCase(private val newsRepository: INewsRepository) : INewsUseCase {
    override suspend fun getHeadlines(category: String): Flow<NewsHeadlinestResponse> = newsRepository.getNewsHeadline(category)

    override fun getNews(): LiveData<PagingData<NewsRoomEntity>> = newsRepository.getNews()
}