package com.example.movies.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Page(
    @PrimaryKey val number: Int, val savedTimeStamp: Long
)