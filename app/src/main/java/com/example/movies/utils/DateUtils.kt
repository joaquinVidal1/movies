package com.example.movies.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Formatter

object DateUtils {

    private const val BACKEND_FORMAT = "yyyy-MM-dd"

    fun String.fromBackendDateToLocalDate(): LocalDate {
        return LocalDate.parse(this)
    }

    fun LocalDate.parseToString():String {
        val formatter = DateTimeFormatter.ofPattern(BACKEND_FORMAT)
        return this.format(formatter)
    }
}
