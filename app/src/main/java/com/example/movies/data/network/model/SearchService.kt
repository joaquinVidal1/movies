package com.example.movies.data.network.model

import retrofit2.http.Body
import retrofit2.http.POST

interface SearchService {

    @POST("search/documents")
    suspend fun searchMovies(
        @Body body: SearchBody
    ): List<ApiMovie>
}