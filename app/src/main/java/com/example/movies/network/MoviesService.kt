package com.example.movies.network

import com.example.movies.network.model.ApiDetailsMovie
import com.example.movies.network.model.ApiMovie
import com.example.movies.network.model.ApiPaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(DISCOVER_MOVIES_PATH)
    suspend fun getMovies(
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): ApiPaginatedResponse<ApiMovie>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): ApiDetailsMovie


    companion object {
        const val DISCOVER_MOVIES_PATH = "discover/movie"
        const val MOVIE_DETAILS_PATH = "movie/"
    }
}