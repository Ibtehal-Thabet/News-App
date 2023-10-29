package com.example.newsapp.data.api

import com.example.newsapp.data.api.model.newsResponse.NewsResponse
import com.example.newsapp.data.api.model.sourcesResponse.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") key: String = ApiConstants.apiKey,
        @Query("category") category: String
    ): SourcesResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") key: String = ApiConstants.apiKey,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
    ): NewsResponse
}