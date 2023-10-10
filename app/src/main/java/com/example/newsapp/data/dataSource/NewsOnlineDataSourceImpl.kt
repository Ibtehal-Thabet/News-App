package com.example.newsapp.data.dataSource

import com.example.newsapp.data.api.WebServices
import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.dataSource.NewsDataSource
import javax.inject.Inject

class NewsOnlineDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    NewsDataSource {
    override suspend fun getNews(sourceId: String): List<News?>? {
        val response = webServices.getNews(sources = sourceId)
        return response.articles
    }
}