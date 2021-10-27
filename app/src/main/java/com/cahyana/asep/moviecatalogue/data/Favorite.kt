package com.cahyana.asep.moviecatalogue.data

import androidx.room.ColumnInfo

data class Favorite(

    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "image_path")
    var imagePath: String,

    @ColumnInfo(name = "score")
    var score: Double,

    @ColumnInfo(name = "ntype")
    var ntype: Int = 0
) {
    val type: FavoriteType
        get() = FavoriteType.values()[ntype]
}

