package com.example.movies.domain.usecase

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<Unit, List<Movie>>() {

    override suspend fun execute(params: Unit): List<Movie> {
        return moviesRepository.getAllMovies()
    }
}