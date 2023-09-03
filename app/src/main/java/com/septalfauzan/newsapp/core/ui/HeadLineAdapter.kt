package com.septalfauzan.newsapp.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.septalfauzan.newsapp.R
import com.septalfauzan.newsapp.core.data.remote.response.NewsItem
import com.septalfauzan.newsapp.core.utils.formatTimeStampDatasource
import com.septalfauzan.newsapp.databinding.HeadlinesItemBinding
import java.util.ArrayList

class HeadLineAdapter(private val onClick: (String) -> Unit) : RecyclerView.Adapter<HeadLineAdapter.ListViewHolder>() {
    private var listData = ArrayList<NewsItem>()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = HeadlinesItemBinding.bind(itemView)

        fun bind(data: NewsItem) {
            with(binding) {
                Glide.with(itemView.context).load(data.urlToImage).into(headlineImage)
                headlineTitle.text = data.title
                headlineAuthor.text = data.author
                headlinePublished.text = data.publishedAt.formatTimeStampDatasource()
                binding.root.setOnClickListener {
                    onClick(data.url)
                }
            }
        }
    }

    fun setData(newListData: List<NewsItem>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.headlines_item, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

}