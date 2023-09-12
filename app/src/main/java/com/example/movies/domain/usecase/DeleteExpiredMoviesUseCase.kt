package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class DeleteExpiredMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<Unit, Unit>() {
    override suspend fun execute(unit: Unit) {
        moviesRepository.deleteExpiredMovies()
    }
}