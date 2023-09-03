package com.septalfauzan.newsapp.core.data.remote.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class NewsRoomEntity(

    @PrimaryKey
    @ColumnInfo(name="url")
    val url: String,

    @ColumnInfo(name="publishedAt")
    val publishedAt: String,

    @ColumnInfo(name="author")
    val author: String,

    @ColumnInfo(name="urlToImage")
    val urlToImage: String,

    @ColumnInfo(name="description")
    val description: String,

    @ColumnInfo(name="source")
    val source: String,

    @ColumnInfo(name="title")
    val title: String,


    @ColumnInfo(name="content")
    val content: String
)

