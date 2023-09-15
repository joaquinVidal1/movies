package com.example.movies.data.repository

import com.example.movies.data.db.MoviesDao
import com.example.movies.data.db.model.DBFavedMovie
import com.example.movies.data.network.MoviesService
import com.example.movies.data.network.model.MOVIE_IMAGE_BASE_URL_400
import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieReviews
import com.example.movies.domain.model.Page
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao, private val moviesService: MoviesService
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
        return moviesService.getMovieDetails(movieId).toModel()
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

    override suspend fun getFavedMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            moviesDao.getFavMovies().map { it.toModel() }
        }
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

}

const val TIME_MOVIE_AVAILABLE = 24 * 60 * 60 * 1000