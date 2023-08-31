package com.example.movies.di

import android.content.Context
import com.example.movies.db.MoviesDao
import com.example.movies.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
            return MoviesDatabase.getMoviesDatabase(appContext)
        }

        @Provides
        fun provideMoviesDao(database: MoviesDatabase): MoviesDao {
            return database.moviesDao
        }

}