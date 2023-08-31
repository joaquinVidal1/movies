package com.example.movies.utils

import java.time.LocalDate

object DateUtils {

    private const val BACKEND_FORMAT = "yyyy-MM-dd"

    fun String.fromBackendDateToLocalDate(): LocalDate {
        return LocalDate.parse(this)
    }
}
