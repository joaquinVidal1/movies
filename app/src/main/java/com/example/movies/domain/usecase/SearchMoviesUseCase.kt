package com.example.movies.domain.usecase

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<SearchMoviesUseCase.Params, List<Movie>>() {

    override suspend fun execute(params: Params): List<Movie> {
        return moviesRepository.searchMovies(params.queryTitle, params.queryOverview)
    }

    data class Params(val queryTitle: String, val queryOverview: String)
}