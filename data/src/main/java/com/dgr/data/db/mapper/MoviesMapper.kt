package com.dgr.data.db.mapper

import com.dgr.data.db.entity.MovieEntity
import com.dgr.data.db.model.Movie

class MoviesMapper {
    fun fromEntity(from: MovieEntity) =
            Movie(
                    imdbID = from.imdbID,
                    title = from.title,
                    year = from.year,
                    runtime = from.runtime,
                    director = from.director,
                    plot = from.plot,
                    poster = from.poster
            )

    fun toEntity(to: Movie): MovieEntity =
            MovieEntity(
                    imdbID = to.imdbID,
                    title = to.title,
                    year = to.year,
                    runtime = to.runtime,
                    director = to.director,
                    plot = to.plot,
                    poster = to.poster
            )
}