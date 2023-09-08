package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class LoadMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<LoadMoviesUseCase.Params, Unit>() {

    data class Params(val page: Int)

    override suspend fun execute(params: Params) {
        moviesRepository.getMovies(params.page)
    }
}