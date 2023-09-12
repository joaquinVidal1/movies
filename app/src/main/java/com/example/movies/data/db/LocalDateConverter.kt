package com.example.movies.data.db

import androidx.room.TypeConverter
import com.example.movies.domain.utils.DateUtils.fromBackendDateToLocalDate
import com.example.movies.domain.utils.DateUtils.parseToString
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
