package com.example.movies.domain.usecase

import com.example.movies.domain.model.MovieReviews
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<GetMovieReviewsUseCase.Params, MovieReviews>() {

    data class Params(val movieId: Int, val page: Int)

    override suspend fun execute(params: Params): MovieReviews {
        return moviesRepository.getMovieReviews(movieId = params.movieId, page = params.page)
    }
}