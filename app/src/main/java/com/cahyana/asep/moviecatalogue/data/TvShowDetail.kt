package com.cahyana.asep.moviecatalogue.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre

@Entity(tableName = "tv_show")
data class TvShowDetail(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "score")
    var score: Double = 0.0,

    @ColumnInfo(name = "year")
    var year: String = "",

    @ColumnInfo(name = "season")
    var season: Int = 0,

    @ColumnInfo(name = "episode")
    var episode: Int = 0,

    @Ignore
    var genres: List<Genre> = arrayListOf(),

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "image_path")
    var imagePath: String = "",

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "detail")
    var detail: Boolean = false
)
