package com.example.movies.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val TOKEN_KEY = "token"

class PreferenceHelper @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private fun getPreferences(): SharedPreferences {
        return appContext.getSharedPreferences("MOVIES_PREFERENCES", Context.MODE_PRIVATE)
    }

    fun insertToken(token: String) {
        val editor = this.getPreferences().edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String {
        return this.getPreferences().getString(TOKEN_KEY, null) ?: ""
    }

    fun removeToken() {
        val editor = this.getPreferences().edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }

}