package com.example.movies.domain.usecase

import com.example.movies.domain.model.MovieReview
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<GetMovieReviewsUseCase.Params, List<MovieReview>>() {

    data class Params(val movieId: Int, val page: Int)

    override suspend fun execute(params: Params): List<MovieReview> {
        return moviesRepository.getMovieReviews(movieId = params.movieId, page = params.page)
    }
}