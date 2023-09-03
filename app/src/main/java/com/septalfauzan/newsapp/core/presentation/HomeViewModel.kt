package com.septalfauzan.newsapp.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.septalfauzan.newsapp.core.data.Resource
import com.septalfauzan.newsapp.core.data.remote.response.NewsItem
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import com.septalfauzan.newsapp.core.domain.usecase.INewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val newsUseCase: INewsUseCase) : ViewModel() {
    private val _news = MutableLiveData<Resource<PagingData<NewsRoomEntity>?>>(Resource.Loading(null))
    val news: LiveData<Resource<PagingData<NewsRoomEntity>?>> get() = _news
    private val _headLines = MutableLiveData<Resource<List<NewsItem>>>(Resource.Loading(null))
    val headLines: LiveData<Resource<List<NewsItem>>> get() = _headLines


    fun getNewsPaging() = newsUseCase.getNews().cachedIn(viewModelScope)
    fun getHeadlines(){
        viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.getHeadlines("technology").catch { e ->
                _headLines.postValue(Resource.Error(e.message.toString()))
            }.collect{data ->
                _headLines.postValue(Resource.Success(data.articles))
            }
        }
    }
}