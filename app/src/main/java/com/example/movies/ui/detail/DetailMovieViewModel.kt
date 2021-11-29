package com.example.movies.ui.detail

import androidx.lifecycle.*
import com.example.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val movieId = MutableLiveData<String>()

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    var movie = Transformations.switchMap(movieId) { movieId ->
        movieUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun addBookmark() {
        val movieResource = movie.value
        movieResource?.data?.let { movieUseCase.setMovieBookmarked(it) }
    }

    fun deleteBookmark() {
        val movieResource = movie.value
        movieResource?.data?.id.let {
            if (it != null) {
                movieUseCase.deleteMovieBookmarked(it)
            }
        }
    }

    var movieBookmarked = Transformations.switchMap(movieId) { movieId ->
        movieUseCase.getMovieBookmarkedById(movieId.toInt()).asLiveData()
    }


}