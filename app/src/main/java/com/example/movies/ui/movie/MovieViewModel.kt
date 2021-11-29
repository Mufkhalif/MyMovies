package com.example.movies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase):ViewModel() {
    val movies = movieUseCase.getMovie().asLiveData()
}