package com.example.movies.repository

import com.example.movies.db.MoviesDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(private val moviesDao: MoviesDao){

}