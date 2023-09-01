package com.example.movies.di

import com.example.movies.repository.MoviesRepository
import com.example.movies.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Binds
    @Singleton
    fun bindMoviesRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository {
        return repositoryImpl
    }
}