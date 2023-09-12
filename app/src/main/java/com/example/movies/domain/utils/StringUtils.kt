package com.example.movies.domain.utils

fun String.addDotsToLongNumber() = this.reversed().chunked(3).joinToString(".").reversed()