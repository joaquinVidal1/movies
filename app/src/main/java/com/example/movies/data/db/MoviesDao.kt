package com.example.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movies.data.db.model.DBMovie
import com.example.movies.data.db.model.DBPage
import com.example.movies.domain.model.Page

@Dao
interface MoviesDao {

    @Query("SELECT * FROM DBMovie ORDER BY DBMovie.pageNumber ASC")
    suspend fun getMoviesAsync(): List<DBMovie>

    @Query("DELETE FROM DBPage WHERE DBPage.savedTimeStamp < :deleteTimeMin")
    fun deleteExpiredMovies(deleteTimeMin: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: DBPage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<DBMovie>)

    @Transaction
    suspend fun insertPageWithMovies(page: Page) {
        insertPage(DBPage(number = page.number, savedTimeStamp = page.savedTimeStamp))
        val moviesWithPageId = page.movies.map { it.toDBModel(pageNumber = page.number) }
        insertMovies(moviesWithPageId)
    }

    @Query("DELETE FROM DBPage")
    suspend fun emptyDatabase()

    @Query("SELECT MAX(number) FROM DBPage")
    fun getLastPage(): Int?

    @Query("SELECT number FROM DBPage ORDER BY number ASC")
    fun getPageNumbers(): List<Int>
}