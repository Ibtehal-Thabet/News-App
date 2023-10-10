package com.example.newsapp.data.dataSource

import com.example.newsapp.data.api.WebServices
import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.dataSource.SourcesDataSource
import javax.inject.Inject

class SourcesOnlineDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    SourcesDataSource {
    override suspend fun getSources(): List<Source?>? {
        val response = webServices.getSources()
        return response.sources
    }
}