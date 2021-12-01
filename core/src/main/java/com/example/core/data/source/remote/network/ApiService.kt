package com.example.core.data.source.remote.network

import com.example.core.BuildConfig
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovie(): MovieResponse

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieLoadmore(
        @Query("page") page: String
    ): MovieResponse

    @GET("movie/{movie}?api_key=${BuildConfig.API_KEY}")
    suspend fun getDetailMovie(
        @Path("movie") movie: String
    ): Movie

}