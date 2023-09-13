package com.example.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Page
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Page")
    suspend fun getMoviesAsync(): List<Page>

    @Query("DELETE FROM Page WHERE Page.savedTimeStamp < :deleteTimeMin")
    fun deleteExpiredMovies(deleteTimeMin: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: Page)

    @Query("DELETE FROM Page")
    suspend fun emptyDatabase()

    @Query("SELECT MAX(number) FROM Page")
    fun getLastPage(): Int?
}