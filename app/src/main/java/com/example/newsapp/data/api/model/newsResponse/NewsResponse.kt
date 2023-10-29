package com.example.newsapp.data.api.model.newsResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<News?>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("q")
    val query: String? = null
) : Parcelable