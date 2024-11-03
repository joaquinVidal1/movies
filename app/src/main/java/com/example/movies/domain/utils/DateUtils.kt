package com.example.movies.domain.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {

    private const val BACKEND_FORMAT = "yyyy-MM-dd"

    fun String.fromBackendDateToLocalDate(): LocalDate? {
        return try {
            LocalDate.parse(this)
        } catch (e: Exception) {
            null
        }
    }

    fun LocalDate.parseToString(): String {
        val formatter = DateTimeFormatter.ofPattern(BACKEND_FORMAT)
        return this.format(formatter)
    }
}
