package com.example.movies.data.repository

import com.example.movies.data.db.MoviesDao
import com.example.movies.data.db.model.DBFavedMovie
import com.example.movies.data.network.IMDBService
import com.example.movies.data.network.MoviesService
import com.example.movies.data.network.model.MOVIE_IMAGE_BASE_URL_400
import com.example.movies.data.network.model.budget
import com.example.movies.data.network.model.duration
import com.example.movies.data.network.model.genres
import com.example.movies.data.network.model.releaseDate
import com.example.movies.data.network.model.revenue
import com.example.movies.data.network.model.voteAverage
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews
import com.example.movies.domain.model.Page
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesService: MoviesService,
    private val imdbService: IMDBService
) : MoviesRepository {

    override suspend fun getAllMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            moviesDao.getMoviesAsync().map { it.toModel() }
        }
    }

    override suspend fun getNextMoviesPage(): List<Movie> {
        return withContext(Dispatchers.IO) {

            val savedPages = moviesDao.getPageNumbers()
            val nextPageNumber = findFirstGap(savedPages)

            val response = moviesService.getMovies(page = nextPageNumber)
            val newPage = Page(
                number = response.page,
                movies = response.results.map { it.toLocalModel() },
                savedTimeStamp = System.currentTimeMillis()
            )
            moviesDao.insertPageWithMovies(newPage)
            newPage.movies
        }
    }

    override suspend fun getMovieDetails(movieId: Int): DetailsMovie {
        return coroutineScope {
            val watchProvidersDeferred = async {
                moviesService.getWatchProviders(movieId).results.let {
                    it["US"]?.rent ?: it["US"]?.buy ?: it["AR"]?.buy ?: listOf()
                }
            }

            val movieDetailsDeferred = async {
                moviesService.getMovieDetails(movieId)
            }

            val imdbDetailsDeferred = async {
                moviesService.getIMDBID(movieId).let {
                    imdbService.getMovieDetails(it.imdbId)
                }
            }

            val watchProviders = watchProvidersDeferred.await()
            val movieDetails = movieDetailsDeferred.await()
            val resultFromTMDB = movieDetails.toModel(watchProviders)

            val imdbDetails = imdbDetailsDeferred.await()
            resultFromTMDB.copy(
                releaseDate = if (resultFromTMDB.releaseDate.isBefore(imdbDetails.releaseDate)) resultFromTMDB.releaseDate else imdbDetails.releaseDate,
                duration = Duration.ofMinutes(
                    resultFromTMDB.duration.toMinutes().getAverage(imdbDetails.duration.toMinutes())
                ),
                voteAverage = resultFromTMDB.voteAverage.getAverage(imdbDetails.voteAverage),
                budget = resultFromTMDB.budget.getAverage(imdbDetails.budget),
                revenue = resultFromTMDB.revenue.getAverage(imdbDetails.revenue),
                genres = (resultFromTMDB.genres + imdbDetails.genres).filterDuplicate()
            )
        }
    }

    private fun Long.getAverage(a: Long): Long = (this + a) / 2

    private fun Float.getAverage(a: Float): Float = (this + a) / 2

    private fun List<String>.filterDuplicate(): List<String> {
        val set = LinkedHashSet<String>()
        forEach { element ->
            if (set.none { it.equals(element, ignoreCase = true) }) {
                set.add(element)
            }
        }
        return set.toList()
    }

    override suspend fun deleteExpiredMovies() {
        withContext(Dispatchers.IO) {
            val minTimeStamp = System.currentTimeMillis() - TIME_MOVIE_AVAILABLE
            moviesDao.deleteExpiredMovies(deleteTimeMin = minTimeStamp)
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviews {
        val response = moviesService.getMovieReviews(movieId)
        return MovieReviews(
            amountOfReviews = response.totalResults, reviews = response.results.map {
                val avatarPath = it.authorDetails?.avatarPath
                it.copy(authorDetails = it.authorDetails?.copy(avatarPath = MOVIE_IMAGE_BASE_URL_400 + avatarPath))
            }, totalPages = response.totalPages
        )
    }

    override suspend fun emptyDatabase() {
        withContext(Dispatchers.IO) {
            moviesDao.emptyDatabase()
        }
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
//        val response = moviesService.changeMovieFavedStatus(
//            body = MovieFavouriteRequestBody(mediaId = movieId, favorite = true)
//        )
        withContext(Dispatchers.IO) {
            moviesDao.addMovieToFav(DBFavedMovie(id = movieId))
        }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
//        moviesService.changeMovieFavedStatus(
//            body = MovieFavouriteRequestBody(mediaId = movieId, favorite = false)
//        )
        withContext(Dispatchers.IO) {
            moviesDao.removeMovieFromFav(movieId = movieId)
        }
    }

    override suspend fun getMovieIsFaved(movieId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            moviesDao.isMovieFaved(movieId)
        }
    }

    override fun getFavedMovies(): Flow<List<Movie>> {
        return moviesDao.getFavMovies().map { it.map { movie -> movie.toModel() } }
    }

    private fun findFirstGap(pageNumbers: List<Int>): Int {
        val sortedNumbers = pageNumbers.sorted()
        for (i in 1 until sortedNumbers.size - 1) {
            if (sortedNumbers[i + 1] - sortedNumbers[i] > 1) {
                return sortedNumbers[i] + 1
            }
        }
        return (sortedNumbers.lastOrNull() ?: 0) + 1
    }

    override suspend fun searchMovies(queryTitle: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            moviesService.searchMovies(queryTitle).results.map { it.toLocalModel() }
        }
    }

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000