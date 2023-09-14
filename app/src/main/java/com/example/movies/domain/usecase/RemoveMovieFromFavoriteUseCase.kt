package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class RemoveMovieFromFavoriteUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<RemoveMovieFromFavoriteUseCase.Params, Unit>() {

    data class Params(val movieId: Int)

    override suspend fun execute(params: Params) {
        moviesRepository.addMovieToFavorite(params.movieId)
    }

}