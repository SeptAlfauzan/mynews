package com.septalfauzan.newsapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsHeadlinestResponse(

    @field:SerializedName("totalResults")
	val totalResults: Int,

    @field:SerializedName("articles")
	val articles: List<NewsItem>,

    @field:SerializedName("status")
	val status: String
)