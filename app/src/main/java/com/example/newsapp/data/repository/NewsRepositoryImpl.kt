package com.example.newsapp.data.repository

import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.dataSource.NewsDataSource
import com.example.newsapp.repository.news.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    @NewsOnlineDataSource
    private val dataSource: NewsDataSource
) : NewsRepository {
    override suspend fun getNews(sourceId: String): List<News?>? {
        return dataSource.getNews(sourceId = sourceId)
    }
}