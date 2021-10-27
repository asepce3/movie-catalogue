package com.cahyana.asep.moviecatalogue.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.cahyana.asep.moviecatalogue.data.MovieDetail

@Entity(
    tableName = "movie_ref",
    primaryKeys = ["movie_id", "genre_id"],
    foreignKeys = [
        ForeignKey(entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"]),
        ForeignKey(entity = Genre::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"]
        )
    ]
)
data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: String,

    @ColumnInfo(name = "genre_id", index = true)
    val genreId: String
)
