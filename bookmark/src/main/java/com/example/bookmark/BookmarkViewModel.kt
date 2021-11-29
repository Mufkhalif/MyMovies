package com.example.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase

class BookmarkViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getMovieBookmarked().asLiveData()
}