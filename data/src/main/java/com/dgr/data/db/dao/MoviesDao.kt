package com.dgr.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dgr.data.db.entity.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllFavourites(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(movieEntity: MovieEntity)

    @Query("DELETE FROM MovieEntity WHERE imdbID = :imdbID")
    fun removeFavorite(imdbID: String)

    @Query("SELECT * FROM MovieEntity WHERE imdbID = :imdbID")
    fun getFavorite(imdbID: String): MovieEntity?

}
