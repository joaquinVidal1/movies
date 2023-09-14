package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class GetIsMovieFavedUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<GetIsMovieFavedUseCase.Params, Boolean>() {

    data class Params(val movieId: Int)

    override suspend fun execute(params: Params): Boolean {
        return moviesRepository.getMovieIsFaved(params.movieId)
    }

}