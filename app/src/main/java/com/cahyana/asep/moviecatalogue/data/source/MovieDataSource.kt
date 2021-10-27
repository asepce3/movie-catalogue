package com.cahyana.asep.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.vo.Resource

interface MovieDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<Movie>>>

    fun findMovies(key: String): LiveData<Resource<PagedList<Movie>>>

    fun getMovieById(id: String): LiveData<Resource<MovieWithGenre>>

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShow>>>

    fun findTvShow(key: String): LiveData<Resource<PagedList<TvShow>>>

    fun getTvShowById(id: String): LiveData<Resource<TvShowWithGenre>>

    fun getAllFavoriteMovies(): LiveData<PagedList<Favorite>>

    fun getAllFavoriteTvShows(): LiveData<PagedList<Favorite>>

    fun findFavoriteMovie(key: String): LiveData<PagedList<Favorite>>

    fun findFavoriteTvShow(key: String): LiveData<PagedList<Favorite>>

    fun saveFavorite(favorite: FavoriteRequest)

    fun deleteFavorite(favorite: FavoriteRequest)
}
