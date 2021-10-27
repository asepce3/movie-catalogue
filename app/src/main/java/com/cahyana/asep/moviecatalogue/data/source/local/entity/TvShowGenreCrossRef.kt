package com.cahyana.asep.moviecatalogue.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.cahyana.asep.moviecatalogue.data.TvShowDetail

@Entity(
    tableName = "tv_show_ref",
    primaryKeys = ["tv_show_id", "genre_id"],
    foreignKeys = [
        ForeignKey(entity = TvShowDetail::class,
            parentColumns = ["id"],
            childColumns = ["tv_show_id"]
        ),
        ForeignKey(entity = Genre::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"]
        )
    ]
)
data class TvShowGenreCrossRef(
    @ColumnInfo(name = "tv_show_id")
    val tvShowId: String,

    @ColumnInfo(name = "genre_id", index = true)
    val genreId: String
)









