package com.example.movies.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.model.Movie
import java.time.LocalDate

@Entity(
    foreignKeys = [ForeignKey(
        entity = DBPage::class,
        parentColumns = arrayOf("number"),
        childColumns = arrayOf("pageNumber"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DBMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val poster: String,
    val releaseDate: LocalDate,
    val pageNumber: Int
) {
    fun toModel(): Movie = Movie(
        id, title, overview, voteAverage, poster, releaseDate
    )

}