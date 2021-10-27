package com.cahyana.asep.moviecatalogue.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.cahyana.asep.moviecatalogue.data.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT id, title, overview, image_path, score, 0 AS ntype FROM movie WHERE favorite = 1 ")
    fun getAllFavoriteMovies(): DataSource.Factory<Int, Favorite>

    @Query("SELECT id, title, overview, image_path, score, 1 AS ntype FROM tv_show WHERE favorite = 1")
    fun getAllFavoriteTvShows(): DataSource.Factory<Int, Favorite>

    @Query("SELECT id, title, overview, image_path, score, 0 AS ntype FROM movie WHERE favorite = 1 AND title LIKE :title")
    fun findFavoriteMovie(title: String): DataSource.Factory<Int, Favorite>

    @Query("SELECT id, title, overview, image_path, score, 1 AS ntype FROM tv_show WHERE favorite = 1 AND title LIKE :title")
    fun findFavoriteTvShow(title: String): DataSource.Factory<Int, Favorite>
}
