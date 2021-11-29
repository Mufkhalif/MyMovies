package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentities WHERE bookmarked = 1")
    fun getMoviesBookmarked(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE id = :id")
    fun getMoviesBookmarkedById(id: Int): Flow<List<MovieEntity>>

    @Query("DELETE FROM movieentities where id = :id")
    fun deleteBookmark(id: Int)
}