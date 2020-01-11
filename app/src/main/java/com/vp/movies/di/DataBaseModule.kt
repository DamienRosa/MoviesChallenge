package com.vp.movies.di

import android.app.Application
import androidx.room.Room
import com.dgr.data.db.dao.MoviesDao
import com.dgr.data.db.mapper.MoviesMapper
import com.dgr.data.db.repository.MovieRepository

import com.dgr.data.db.MoviesDataBase
import com.vp.movies.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): MoviesDataBase = Room.databaseBuilder(
            application,
            MoviesDataBase::class.java,
            BuildConfig.DataBaseName
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(database: MoviesDataBase): MoviesDao {
        return database.getMoviesDao()
    }

    @Singleton
    @Provides
    fun provideMovieMapper() = MoviesMapper()

    @Singleton
    @Provides
    fun provideMovieRepository(dao: MoviesDao, mapper: MoviesMapper): MovieRepository = MovieRepository(dao, mapper)
}