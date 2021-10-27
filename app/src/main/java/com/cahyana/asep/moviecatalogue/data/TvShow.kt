package com.cahyana.asep.moviecatalogue.data

import androidx.room.ColumnInfo

data class TvShow(

    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "score")
    var score: Double,

    @ColumnInfo(name = "year")
    var year: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "image_path")
    var imagePath: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)