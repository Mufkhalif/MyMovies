package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.domain.model.Movie

object DataMapper {

    fun mapDomainToEntities(it: Movie): MovieEntity =
        MovieEntity(
            id = it.id,
            title = it.title,
            overview = it.overview,
            releaseDate = it.releaseDate,
            popularity = it.popularity,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            bookmarked = true
        )

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                posterPath = it.posterPath,
                bookmarked = it.bookmarked
            )
        }
}