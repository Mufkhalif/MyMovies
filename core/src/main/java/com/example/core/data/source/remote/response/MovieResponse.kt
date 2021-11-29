package com.example.core.data.source.remote.response

import com.example.core.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,

    @field:SerializedName("total_pages")
    val totalPages: Int
)