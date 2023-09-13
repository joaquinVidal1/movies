package com.example.movies.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie")
    suspend fun getMoviesAsync():List<Movie>

    @Query("DELETE FROM Movie WHERE Movie.savedTimeStamp < :deleteTimeMin")
    fun deleteExpiredMovies(deleteTimeMin: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vies: List<Movie>)

    @Query("DELETE FROM Movie")
    suspend fun emptyDb()
}