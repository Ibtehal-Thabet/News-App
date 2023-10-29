package com.example.newsapp.repository.news

import com.example.newsapp.data.api.model.newsResponse.News

interface NewsRepository {

    suspend fun getNews(sourceId: String): List<News?>?

    suspend fun getSearchedNews(query: String): List<News?>?
}