package com.cahyana.asep.moviecatalogue.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.cahyana.asep.moviecatalogue.data.TvShowDetail

data class TvShowWithGenre(
    @Embedded
    val tvShow: TvShowDetail,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(TvShowGenreCrossRef::class, parentColumn = "tv_show_id", entityColumn = "genre_id"),
        entity = Genre::class
    )
    var genres: List<Genre>
)
