package com.example.movies.data.network.model

import com.example.movies.domain.model.DetailsMovie
import com.squareup.moshi.Json

data class ApiDetailsMovie(
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "imdb_id") val imdbId: String,
    @Json(name = "video") val video: Boolean,
    @Json(name = "title") val title: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "revenue") val revenue: Long,
    @Json(name = "genres") val genres: List<Genre>,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountriesItem>?,
    @Json(name = "id") val id: Int,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "budget") val budget: Int,
    @Json(name = "overview") val overview: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "runtime") val runtime: Int,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguagesItem>?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompaniesItem>?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "belongs_to_collection") val belongsToCollection: BelongsToCollection?,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "adult") val adult: Boolean = false,
    @Json(name = "homepage") val homepage: String,
    @Json(name = "status") val status: String
) {
    fun toModel(): DetailsMovie {
        return DetailsMovie(
            id = id,
            title = title,
            genres = genres.map { it.name },
            overview = overview,
            voteAverage = voteAverage,
            peopleWatching = popularity.toString().replace(".", "").toInt(),
            posterPath = MOVIE_IMAGE_BASE_URL_400 + posterPath,
            videoPreviewPath = MOVIE_IMAGE_BASE_URL_400 + backdropPath,
        )
    }
}