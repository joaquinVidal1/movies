package com.example.movies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.movies.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        @Volatile
        private lateinit var MOVIESDBINSTANCE: MoviesDatabase

        fun getMoviesDatabase(context: Context): MoviesDatabase {
            synchronized(this) {
                if (!Companion::MOVIESDBINSTANCE.isInitialized) {
                    MOVIESDBINSTANCE = Room.databaseBuilder(
                        context.applicationContext, MoviesDatabase::class.java, "movies"
                    ).build()
                }
            }
            return MOVIESDBINSTANCE
        }
    }
}