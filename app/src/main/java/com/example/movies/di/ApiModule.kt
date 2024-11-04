package com.example.movies.di

import com.example.movies.data.network.IMDBService
import com.example.movies.data.network.MoviesService
import com.example.movies.data.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesService {
        return RetrofitFactory.getBuilder().create(MoviesService::class.java)
    }

    @Provides
    @Singleton
    fun provideIMDBService(): IMDBService {
        return RetrofitFactory.getIMDBBuilder().create(IMDBService::class.java)
    }
}
