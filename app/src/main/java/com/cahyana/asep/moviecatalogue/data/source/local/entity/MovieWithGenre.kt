package com.cahyana.asep.moviecatalogue.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.cahyana.asep.moviecatalogue.data.MovieDetail

data class MovieWithGenre(
    @Embedded
    val movie: MovieDetail,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MovieGenreCrossRef::class, parentColumn = "movie_id",
            entityColumn = "genre_id"),
        entity = Genre::class
    )
    var genres: List<Genre>
)
