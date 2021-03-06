package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getMovie(): Flow<ApiResponse<List<Movie>>> {
        return flow {
            try {
                val response = apiService.getMovie()
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.d("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieLoadmore(page: String): Flow<ApiResponse<List<Movie>>> {
        return flow {
            try {
                val response = apiService.getMovieLoadmore(page)
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.d("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieLoadmoreNoFlow(page: String): ApiResponse<List<Movie>> {
        return try {
            val response = apiService.getMovieLoadmore(page)
            val data = response.results
            if (data.isNotEmpty()) {
                ApiResponse.Success(data)
            } else {
                ApiResponse.Empty
            }
        } catch (e: Exception) {
            Log.d("RemoteDataSource", e.toString())
            ApiResponse.Error(e.toString());
        }
    }

    fun getMovieDetail(id: String): Flow<ApiResponse<Movie>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.d("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}