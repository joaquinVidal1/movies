package com.example.movies.db

import androidx.room.TypeConverter
import com.example.movies.utils.DateUtils.fromBackendDateToLocalDate
import com.example.movies.utils.DateUtils.parseToString
import java.time.LocalDate
import java.time.format.DateTimeFormatter


object Converters {
    @TypeConverter
    fun fromString(value: String): LocalDate {
        return value.fromBackendDateToLocalDate()
    }

    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.parseToString()
    }
}
