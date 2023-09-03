package com.septalfauzan.newsapp.core.data.remote.network

import com.septalfauzan.newsapp.core.data.remote.response.NewsHeadlinestResponse
import com.septalfauzan.newsapp.core.data.remote.response.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadline(@Query("category") category: String, @Query("country") country: String = "us", @Query("apiKey") apiKey: String) : NewsHeadlinestResponse

    @GET("everything")
    suspend fun getNews(@Query("page") page: Int, @Query("domains") domain: String = "techcrunch.com", @Query("apiKey") apiKey: String) : NewsListResponse
}