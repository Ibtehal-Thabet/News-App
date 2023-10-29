package com.example.newsapp.data.api.model.sourcesResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourcesResponse(

    @field:SerializedName("sources")
    val sources: List<Source?>? = null,

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

    @field:SerializedName("publishAt")
    val publishAt: String? = null,

    @field:SerializedName("q")
    val query: String? = null

) : Parcelable