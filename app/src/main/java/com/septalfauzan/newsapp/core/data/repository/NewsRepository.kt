package com.septalfauzan.newsapp.core.data.repository

import android.util.Log
import com.septalfauzan.newsapp.core.data.local.room.NewsDatabase
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.septalfauzan.newsapp.BuildConfig
import com.septalfauzan.newsapp.core.data.NewsRemoteMediator
import com.septalfauzan.newsapp.core.data.remote.network.NewsApiService
import com.septalfauzan.newsapp.core.data.remote.response.NewsHeadlinestResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import com.septalfauzan.newsapp.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NewsRepository(
    private val database: NewsDatabase,
    private val newsApiService: NewsApiService
) : INewsRepository {
    override suspend fun getNewsHeadline(category: String): Flow<NewsHeadlinestResponse> {
        try {
            return flowOf(newsApiService.getTopHeadline(category = category, country = "us", apiKey = BuildConfig.NEWS_API_KEY))
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override fun getNews(): LiveData<PagingData<NewsRoomEntity>> {
        try {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 1
                ),
                remoteMediator = NewsRemoteMediator(database, newsApiService),
                pagingSourceFactory = {
                    database.newsDao().getNews()
                }
            ).liveData
        } catch (e: java.lang.Exception) {
            throw e
        }
    }


    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(
            database: NewsDatabase,
            apiService: NewsApiService
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(database, apiService)
            }
    }
}