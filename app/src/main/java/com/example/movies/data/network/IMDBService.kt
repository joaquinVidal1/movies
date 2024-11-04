package com.example.movies.data.network

import com.example.movies.data.network.model.IMDBDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface IMDBService {

    @GET("search")
    suspend fun getMovieDetails(@Query("tt") id: String): IMDBDetails
}