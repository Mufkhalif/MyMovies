package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.MovieDao
import com.example.core.domain.model.Movie
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getMoviesBookmarked(): Flow<List<MovieEntity>> = movieDao.getMoviesBookmarked()

    fun insertMovieBookmarked(movie: Movie) =
        movieDao.insertMovie(DataMapper.mapDomainToEntities(movie))

    fun getMoviesBookmarkedById(id: Int): Flow<List<MovieEntity>> = movieDao.getMoviesBookmarkedById(id)

    fun deleteMovieBookmarked(id: Int) = movieDao.deleteBookmark(id)

}