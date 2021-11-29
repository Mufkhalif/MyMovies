package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: String): Flow<Resource<Movie>>
    fun getMovieBookmarked(): Flow<List<Movie>>
    fun setMovieBookmarked(movie: Movie)
    fun deleteMovieBookmarked(id: Int)
    fun getMovieBookmarkedById(id: Int): Flow<List<Movie>>
}