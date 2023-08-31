package com.example.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        @Volatile
        private lateinit var MOVIESDBINSTANCE: MoviesDatabase

        fun getMoviesDatabase(context: Context): MoviesDatabase {
            synchronized(this) {
                if (!::MOVIESDBINSTANCE.isInitialized) {
                    MOVIESDBINSTANCE = Room.databaseBuilder(
                        context.applicationContext, MoviesDatabase::class.java, "movies"
                    ).build()
                }
            }
            return MOVIESDBINSTANCE
        }
    }
}