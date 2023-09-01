package com.example.movies.network.model

import com.squareup.moshi.Json

class ApiPaginatedResponse<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)