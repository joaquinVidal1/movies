package com.example.movies.db

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.movies.model.Movie

@Dao
interface MoviesDao {

  @Query("SELECT * FROM Movie")
  fun getMovies(): List<Movie>

  @Query("DELETE FROM Movie WHERE Movie.savedTimeStamp < :deleteTimeMin")
  fun deleteExpiredMovies(deleteTimeMin: Long)

  @Upsert
  suspend fun insertMovies(vies: List<Movie>)
}