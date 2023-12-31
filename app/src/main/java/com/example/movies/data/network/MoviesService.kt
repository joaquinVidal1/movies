package com.example.movies.data.network

import com.example.movies.data.network.model.ApiDetailsMovie
import com.example.movies.data.network.model.ApiMovie
import com.example.movies.data.network.model.ApiPaginatedResponse
import com.example.movies.domain.model.MovieReview
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

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int
    ): ApiPaginatedResponse<MovieReview>


    companion object {
        const val DISCOVER_MOVIES_PATH = "discover/movie"
        const val MOVIE_DETAILS_PATH = "movie/"

    }
}