package com.septalfauzan.newsapp.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.formatTimeStampDatasource(): String{
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        val data = inputFormat.parse(this)

        outputFormat.format(data)
    }catch (e: java.lang.Exception){
        e.printStackTrace()
        this
    }
}