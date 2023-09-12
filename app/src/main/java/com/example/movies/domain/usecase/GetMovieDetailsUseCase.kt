package com.example.movies.domain.usecase

import com.example.movies.domain.model.DetailsMovie
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.usecase.base.CoroutineUseCase
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    CoroutineUseCase<GetMovieDetailsUseCase.Params, DetailsMovie>() {

    data class Params(val movieId: Int)

    override suspend fun execute(params: Params): DetailsMovie {
        return moviesRepository.getMovieDetails(params.movieId)
    }
}
