package com.example.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun getMovies(): Flow<List<Movie>>

    @Query("DELETE FROM Movie WHERE Movie.savedTimeStamp < :deleteTimeMin")
    fun deleteExpiredMovies(deleteTimeMin: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vies: List<Movie>)
}