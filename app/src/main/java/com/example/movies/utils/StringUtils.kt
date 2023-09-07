package com.example.movies.utils

fun String.addDotsToLongNumber() = this.reversed().chunked(3).joinToString(".").reversed()