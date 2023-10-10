package com.example.newsapp.data.dataSource

import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.dataSource.NewsDataSource
import javax.inject.Inject

class NewsOfflineDataSourceImpl @Inject constructor() : NewsDataSource {
    override suspend fun getNews(sourceId: String): List<News?>? {
        return listOf()
    }
}