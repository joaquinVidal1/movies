package com.example.movies.domain.usecase

import androidx.lifecycle.LiveData
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class ObserveMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): LiveData<List<Movie>> {
        return moviesRepository.movies
    }

}