package com.septalfauzan.newsapp.core.di

import com.septalfauzan.newsapp.core.data.local.room.NewsDatabase
import android.content.Context
import com.septalfauzan.newsapp.core.data.remote.network.ApiConfig
import com.septalfauzan.newsapp.core.data.repository.NewsRepository
import com.septalfauzan.newsapp.core.domain.repository.INewsRepository
import com.septalfauzan.newsapp.core.domain.usecase.INewsUseCase
import com.septalfauzan.newsapp.core.domain.usecase.NewsUseCase

object Injection {
    private fun provideNewsRepository(context: Context) : INewsRepository{
        val newsApiService = ApiConfig.provideApiService()
        val database = NewsDatabase.getInstance(context)
        return NewsRepository.getInstance(database, newsApiService)
    }

    fun provideNewsUseCase(context: Context): INewsUseCase{
        val newsRepository = provideNewsRepository(context)
        return NewsUseCase(newsRepository)
    }
}