package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getMovie(): Flow<Resource<List<Movie>>> = movieRepository.getMovie()
    override fun getMovieLoadmore(page: String, oldData: List<Movie>): Flow<Resource<List<Movie>>> =
        movieRepository.getMovieLoadmore(page, oldData)

    override suspend fun getMovieLoadmoreNoFlow(page: String): ApiResponse<List<Movie>> =
        movieRepository.getMovieLoadmoreNoFlow(page)

    override fun getDetailMovie(id: String): Flow<Resource<Movie>> =
        movieRepository.getDetailMovie(id)

    override fun getMovieBookmarked(): Flow<List<Movie>> = movieRepository.getMovieBookmarked()

    override fun setMovieBookmarked(movie: Movie) = movieRepository.setMovieBookmarked(movie)

    override fun deleteMovieBookmarked(id: Int) = movieRepository.deleteMovieBookmarked(id)

    override fun getMovieBookmarkedById(id: Int) = movieRepository.getMovieBookmarkedById(id)
}