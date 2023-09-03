package com.septalfauzan.newsapp.core.data

import android.util.Log
import com.septalfauzan.newsapp.core.data.local.room.NewsDatabase
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.septalfauzan.newsapp.BuildConfig
import com.septalfauzan.newsapp.core.data.local.entity.NewsRemoteKeys
import com.septalfauzan.newsapp.core.data.remote.network.NewsApiService
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import com.septalfauzan.newsapp.core.utils.DataMapper.toNewsRoomEntity

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: NewsApiService
) : RemoteMediator<Int, NewsRoomEntity>() {

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsRoomEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKeys?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKeys ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKeys ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val response = apiService.getNews(page = page, apiKey = BuildConfig.NEWS_API_KEY)
            val endOfPaginationReached = response.articles.isEmpty()

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.newsRemoteKeysDao().deleteRemoteKeys()
                    database.newsDao().deleteAll()
                }
                val prevKey = if(page == 1) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1

                val keys = response.articles.map {
                    NewsRemoteKeys(id = it.url, prevKeys = prevKey, nextKeys = nextKey)
                }
                val roomEntities = response.articles.map { it.toNewsRoomEntity() }
                database.newsRemoteKeysDao().insertAll(keys)
                database.newsDao().insert(roomEntities)
            }

            return MediatorResult.Success(endOfPaginationReached)
        }catch (e: java.lang.Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, NewsRoomEntity>): NewsRemoteKeys?{
        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.newsRemoteKeysDao().getRemoteKeysId(data.url)
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, NewsRoomEntity>): NewsRemoteKeys?{
        return state.pages.firstOrNull{ it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.newsRemoteKeysDao().getRemoteKeysId(data.url)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NewsRoomEntity>): NewsRemoteKeys?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                database.newsRemoteKeysDao().getRemoteKeysId(url)
            }
        }
    }
}