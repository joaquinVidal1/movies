package com.example.movies.model.exceptions

import android.content.Context
import com.example.movies.R
import java.io.IOException

class NoInternetException(private val context: Context) : IOException() {
    override val message: String
        get() = context.getString(R.string.internet_connection_error)
}