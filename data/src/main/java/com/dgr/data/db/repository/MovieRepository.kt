package com.dgr.data.db.repository

import com.dgr.data.db.dao.MoviesDao
import com.dgr.data.db.mapper.MoviesMapper
import com.dgr.data.db.model.Movie

class MovieRepository(private val movieDao: MoviesDao,
                      private val moviesMapper: MoviesMapper) {

    fun getAllFavorite(): List<Movie> =
            movieDao.getAllFavourites().map { moviesMapper.fromEntity(it) }

    fun addMovie(movie: Movie) {
        movieDao.addFavorite(moviesMapper.toEntity(movie))
    }

    fun removeMovie(imdbID: String) {
        movieDao.removeFavorite(imdbID)
    }

    fun getFavorite(imdbID: String): Movie? =
            movieDao.getFavorite(imdbID).let {
                if (it != null) { moviesMapper.fromEntity(it) } else { null }
            }
}