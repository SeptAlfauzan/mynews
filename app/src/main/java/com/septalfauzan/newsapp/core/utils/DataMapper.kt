package com.septalfauzan.newsapp.core.utils

import com.septalfauzan.newsapp.core.data.remote.response.NewsItem
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity

object DataMapper {
    fun NewsItem.toNewsRoomEntity() = NewsRoomEntity(
        url = this.url,
        urlToImage = this.urlToImage,
        title = this.title,
        author = this.author,
        description = this.description,
        source = this.source.name,
        content = this.content,
        publishedAt = this.publishedAt,
    )
}