package com.example.movies.ui.movie

import androidx.lifecycle.*
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _storeMovies = MutableLiveData<List<Movie>>()
    private val storeMovies: LiveData<List<Movie>> = _storeMovies

    private val _isLoadingLoadmore = MutableLiveData(false)
    val isLoadingLoadmore: LiveData<Boolean> = _isLoadingLoadmore

    private val _listMoreMovie = MutableLiveData<List<Movie>>()
    val listMoreMovie: LiveData<List<Movie>> = _listMoreMovie

    val movies = movieUseCase.getMovie().asLiveData()

    fun setToStore(movies: List<Movie>) {
        this._storeMovies.value = movies
    }

    private val _offset = MutableLiveData(2)
    private val offset: LiveData<Int> = _offset


    fun loadMoreData() = viewModelScope.launch {
        if (!isLoadingLoadmore.value!!) {
            _isLoadingLoadmore.value = true

            when (val response = movieUseCase.getMovieLoadmoreNoFlow(offset.value.toString())) {
                is ApiResponse.Success -> {
                    val data = response.data
                    val newData = ArrayList<Movie>()

                    storeMovies.value?.let { newData.addAll(it) }
                    newData.addAll(data)

                    _listMoreMovie.value = newData
                    _isLoadingLoadmore.value = false
                    _offset.value = offset.value?.plus(1)
                }
                else -> _isLoadingLoadmore.value = false
            }
        }

    }

}