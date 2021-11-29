package com.example.core.data.source

import com.example.core.data.Resource
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.*


class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getMovie(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        when (val apiResponse = remoteDataSource.getMovie().first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Empty())
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }


    override fun getDetailMovie(id: String): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())

        when (val apiResponse = remoteDataSource.getMovieDetail(id).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Empty())
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMovieBookmarked(): Flow<List<Movie>> {
        return localDataSource.getMoviesBookmarked().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setMovieBookmarked(movie: Movie) {
        appExecutors.diskIO().execute { localDataSource.insertMovieBookmarked(movie) }
    }

    override fun deleteMovieBookmarked(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteMovieBookmarked(id) }
    }

    override fun getMovieBookmarkedById(id: Int): Flow<List<Movie>> {
        return localDataSource.getMoviesBookmarkedById(id).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

}