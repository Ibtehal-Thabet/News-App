package com.example.newsapp.data.repository

import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.dataSource.SourcesDataSource
import com.example.newsapp.repository.sourcesRepository.SourcesRepository
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(private val sourcesOnlineDataSource: SourcesDataSource) :
    SourcesRepository {
    override suspend fun getSources(): List<Source?>? {
        val sources = sourcesOnlineDataSource.getSources()
        return sources
    }
}