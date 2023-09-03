package com.septalfauzan.newsapp.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.septalfauzan.newsapp.R
import com.septalfauzan.newsapp.core.data.remote.response.NewsItem
import com.septalfauzan.newsapp.core.data.remote.response.NewsRoomEntity
import com.septalfauzan.newsapp.core.utils.formatTimeStampDatasource
import com.septalfauzan.newsapp.databinding.NewsItemBinding
import java.util.ArrayList

class NewsAdapter(private val onClick: (id: String) -> Unit) :  PagingDataAdapter<NewsRoomEntity, NewsAdapter.ListViewHolder>(DIFF_CALLBACK){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NewsItemBinding.bind(itemView)

        fun bind(data: NewsRoomEntity) {
            with(binding) {
                Glide.with(itemView.context).load(data.urlToImage).into(newsImage)
                newsTitle.text = data.title
                newsAuthor.text = data.author
                published.text = data.publishedAt.formatTimeStampDatasource()
                binding.root.setOnClickListener {
                    onClick(data.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        Log.d("TAG", "onBindViewHolder: $data")
        if(data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsRoomEntity>() {
            override fun areItemsTheSame(oldItem: NewsRoomEntity, newItem: NewsRoomEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: NewsRoomEntity, newItem: NewsRoomEntity): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
}