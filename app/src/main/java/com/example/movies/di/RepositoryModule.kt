package com.example.movies.di

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository
}