package com.example.movies.di

import com.example.movies.network.MoviesService
import com.example.movies.network.RetrofitFactory
import com.example.movies.utils.PreferenceHelper
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
    fun provideMoviesApi(
        preferenceHelper: PreferenceHelper
    ): MoviesService {
        return RetrofitFactory.getBuilder(getToken = {
            preferenceHelper.getToken()
        }).create(MoviesService::class.java)
    }
}