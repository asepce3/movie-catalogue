package com.cahyana.asep.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieGenreCrossRef
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre

@Dao
abstract class MovieDao {

    @Query("SELECT id, title, release, score, overview, image_path, " +
            "favorite  FROM movie")
    abstract fun getAllMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT id, title, release, score, overview, image_path, " +
        "favorite FROM movie WHERE title LIKE :title")
    abstract fun findMovieByTitle(title: String): DataSource.Factory<Int, Movie>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id")
    abstract fun getMovieDetail(id: String): LiveData<MovieWithGenre>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun _save(genres: List<Genre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveDetails(details: List<MovieDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveMovieRef(genres: List<MovieGenreCrossRef>)

    @Query("SELECT * FROM genre WHERE id=:id")
    abstract fun isGenreExists(id: String): Genre?

    @Query("UPDATE movie SET favorite=:state WHERE id=:id")
    abstract fun saveMovieFavorite(id: String, state: Int)

    @Query("UPDATE movie SET duration=:duration, detail=1 WHERE id=:id")
    abstract fun updateDetail(id: String, duration: Int)

    @Transaction
    open fun save(movieDetail: MovieDetail) {
        val genres = movieDetail.genre
        saveGenre(genres)
        saveMovieDetail(movieDetail)
        saveMovieRef(movieDetail)
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

    private fun saveMovieDetail(movieDetail: MovieDetail) {
        updateDetail(movieDetail.id, movieDetail.duration)
    }

    private fun saveMovieRef(movieDetail: MovieDetail) {
        val listRef = arrayListOf<MovieGenreCrossRef>()
        movieDetail.genre.forEach {
            listRef.add(MovieGenreCrossRef(movieDetail.id, it.id))
        }
        _saveMovieRef(listRef)
    }
}