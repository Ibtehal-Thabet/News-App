package com.example.newsapp.ui.home.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var newsList: List<News?>? = null) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val viewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList!![position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    fun bindNews(articles: List<News?>?) {
        newsList = articles
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: News?) {
            itemBinding.news = news
            itemBinding.invalidateAll()
        }
    }
}