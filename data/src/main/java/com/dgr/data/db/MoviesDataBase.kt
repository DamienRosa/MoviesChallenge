package com.dgr.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dgr.data.db.dao.MoviesDao
import com.dgr.data.db.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}