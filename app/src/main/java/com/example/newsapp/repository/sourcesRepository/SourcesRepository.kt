package com.example.newsapp.repository.sourcesRepository

import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.ui.categories.Category

interface SourcesRepository {
    suspend fun getSources(category: Category): List<Source?>?
}