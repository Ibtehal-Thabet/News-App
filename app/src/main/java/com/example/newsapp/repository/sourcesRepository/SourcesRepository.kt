package com.example.newsapp.repository.sourcesRepository

import com.example.newsapp.data.api.model.sourcesResponse.Source

interface SourcesRepository {
    suspend fun getSources(): List<Source?>?
}