package com.example.movies.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class WatchProvider(
    @Json(name = "logo_path") val logoPath: String,
    @Json(name = "provider_id") val providerId: Int,
    @Json(name = "provider_name") val providerName: String,
    @Json(name = "display_priority") val displayPriority: Int
)

@JsonClass(generateAdapter = true)
data class MediaAvailability(
    val link: String,
    val flatrate: List<WatchProvider>? = null,
    val buy: List<WatchProvider>? = null,
    val rent: List<WatchProvider>? = null
)

@JsonClass(generateAdapter = true)
data class WatchProviderResponse(
    val id: Int, val results: Map<String, MediaAvailability>
)


