package com.example.newsapp.dataSource

import com.example.newsapp.data.api.model.sourcesResponse.Source

interface SourcesDataSource {
    suspend fun getSources(): List<Source?>?
}