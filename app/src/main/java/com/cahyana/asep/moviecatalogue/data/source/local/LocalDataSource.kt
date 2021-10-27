package com.cahyana.asep.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.room.FavoriteDao
import com.cahyana.asep.moviecatalogue.data.source.local.room.MovieDao
import com.cahyana.asep.moviecatalogue.data.source.local.room.TvShowDao

class LocalDataSource private constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val favoriteDao: FavoriteDao
) {

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao, tvShowDao: TvShowDao, favoriteDao: FavoriteDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao, tvShowDao, favoriteDao).apply { instance = this }
            }
    }

    fun getAllMovies(): DataSource.Factory<Int, Movie> = movieDao.getAllMovies()

    fun findMovies(key: String): DataSource.Factory<Int, Movie> = movieDao.findMovieByTitle("%$key%")

    fun saveMovieList(list: List<Movie>) {
        val details = arrayListOf<MovieDetail>()
        list.forEach {
            details.add(movieToDetail(it))
        }
        if (details.isNotEmpty()) {
            movieDao.saveDetails(details)
        }
    }

    fun getAllTvShow(): DataSource.Factory<Int, TvShow> = tvShowDao.getAllTvShows()

    fun findTvShow(key: String): DataSource.Factory<Int, TvShow> = tvShowDao.findTvShows("%$key%")

    fun saveTvShowList(list: List<TvShow>) {
        val details = arrayListOf<TvShowDetail>()
        list.forEach {
            details.add(tvToDetail(it))
        }
        if (details.isNotEmpty()) {
            tvShowDao.saveDetails(details)
        }
    }

    fun getDetailMovieById(id: String): LiveData<MovieWithGenre> = movieDao.getMovieDetail(id)

    fun getDetailTvShowById(id: String): LiveData<TvShowWithGenre> = tvShowDao.getTvShowDetail(id)

    fun saveDetailMovie(detail: MovieDetail) {
        movieDao.save(detail)
    }

    fun saveDetailTvShow(detail: TvShowDetail) {
        tvShowDao.save(detail)
    }

    fun getAllFavoriteMovies(): DataSource.Factory<Int, Favorite> = favoriteDao.getAllFavoriteMovies()

    fun getAllFavoriteTvShows(): DataSource.Factory<Int, Favorite> = favoriteDao.getAllFavoriteTvShows()

    fun saveFavorite(favorite: FavoriteRequest) {
        when (favorite.type) {
            FavoriteType.MOVIE -> movieDao.saveMovieFavorite(favorite.id, FavoriteState.FAVORITE.ordinal)
            FavoriteType.TVSHOW -> tvShowDao.saveTvShowFavorite(favorite.id, FavoriteState.FAVORITE.ordinal)
        }
    }

    fun deleteFavorite(favorite: FavoriteRequest) {
        when (favorite.type) {
            FavoriteType.MOVIE -> movieDao.saveMovieFavorite(favorite.id, FavoriteState.NONE.ordinal)
            FavoriteType.TVSHOW -> tvShowDao.saveTvShowFavorite(favorite.id, FavoriteState.NONE.ordinal)
        }
    }

    fun findFavoriteMovie(key: String): DataSource.Factory<Int, Favorite> = favoriteDao.findFavoriteMovie("%$key%")

    fun findFavoriteTvShow(key: String): DataSource.Factory<Int, Favorite> = favoriteDao.findFavoriteTvShow("%$key%")

    private fun movieToDetail(movie: Movie): MovieDetail = MovieDetail(
        movie.id,
        movie.title,
        movie.release,
        movie.score,
        arrayListOf(),
        0,
        movie.overview,
        movie.imagePath,
        movie.favorite
    )

    private fun tvToDetail(tvShow: TvShow): TvShowDetail = TvShowDetail(
        tvShow.id,
        tvShow.title,
        tvShow.score,
        tvShow.year,
        0,
        0,
        arrayListOf(),
        tvShow.overview,
        tvShow.imagePath,
        tvShow.favorite
    )
}