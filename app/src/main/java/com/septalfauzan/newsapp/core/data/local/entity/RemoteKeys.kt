package com.septalfauzan.newsapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_remote_keys")
data class NewsRemoteKeys(
    @PrimaryKey val id: String,
    val prevKeys: Int?,
    val nextKeys: Int?
)
