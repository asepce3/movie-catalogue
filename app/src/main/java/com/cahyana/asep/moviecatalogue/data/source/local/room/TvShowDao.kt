package com.cahyana.asep.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.cahyana.asep.moviecatalogue.data.TvShow
import com.cahyana.asep.moviecatalogue.data.TvShowDetail
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowGenreCrossRef
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre

@Dao
abstract class TvShowDao {

    @Query("SELECT id, title, score, year, overview, image_path, favorite FROM tv_show")
    abstract fun getAllTvShows(): DataSource.Factory<Int, TvShow>

    @Query("SELECT id, title, score, year, overview, image_path, favorite FROM tv_show WHERE title LIKE :title")
    abstract fun findTvShows(title: String): DataSource.Factory<Int, TvShow>

    @Transaction
    @Query("SELECT * FROM tv_show WHERE id = :id")
    abstract fun getTvShowDetail(id: String): LiveData<TvShowWithGenre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveDetails(details: List<TvShowDetail>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun _save(genres: List<Genre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveRef(ref: List<TvShowGenreCrossRef>)

    @Query("SELECT * FROM genre WHERE id=:id")
    abstract fun isGenreExists(id: String): Genre?

    @Query("UPDATE tv_show SET favorite=:state WHERE id=:id")
    abstract fun saveTvShowFavorite(id: String, state: Int)

    @Query("UPDATE tv_show SET season=:season, episode=:episode, detail=1")
    abstract fun updateTvDetail(season: Int, episode: Int)

    @Update
    abstract fun update(tvShowDetail: TvShowDetail)

    @Transaction
    open fun save(tvShowDetail: TvShowDetail) {
        val genres = tvShowDetail.genres
        saveGenre(genres)
        saveTvDetail(tvShowDetail)
        saveTvRef(tvShowDetail)
    }

    private fun saveGenre(genres: List<Genre>) {
        val listGenre = arrayListOf<Genre>()
        var genre: Genre?
        for (g in genres) {
            genre = isGenreExists(g.id)
            if (genre == null) {
                listGenre.add(g)
            }
        }

        if (listGenre.isNotEmpty())
            _save(listGenre)
    }

    private fun saveTvDetail(tvShowDetail: TvShowDetail) {
        updateTvDetail(tvShowDetail.season, tvShowDetail.episode)
        if (tvShowDetail.genres.isNotEmpty()) {
            saveTvRef(tvShowDetail)
        }
    }

    private fun saveTvRef(tvShowDetail: TvShowDetail) {
        val listRef = arrayListOf<TvShowGenreCrossRef>()
        tvShowDetail.genres.forEach {
            listRef.add(TvShowGenreCrossRef(tvShowDetail.id, it.id))
        }
        if (listRef.isNotEmpty()) {
            _saveRef(listRef)
        }

    }
}