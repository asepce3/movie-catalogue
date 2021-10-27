package com.cahyana.asep.moviecatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.vo.Resource

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllMovies(): LiveData<Resource<PagedList<Movie>>> = movieRepository.getAllMovies()

    fun findMovies(key: String): LiveData<Resource<PagedList<Movie>>> = movieRepository.findMovies(key)

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShow>>> = movieRepository.getAllTvShows()

    fun findTvShow(key: String): LiveData<Resource<PagedList<TvShow>>> = movieRepository.findTvShow(key)

    fun getAllFavoriteMovies(): LiveData<PagedList<Favorite>> = movieRepository.getAllFavoriteMovies()

    fun getAllFavoriteTvShows(): LiveData<PagedList<Favorite>> = movieRepository.getAllFavoriteTvShows()

    fun saveFavorite(favoriteRequest: FavoriteRequest) {
        movieRepository.saveFavorite(favoriteRequest)
    }

    fun deleteFavorite(favoriteRequest: FavoriteRequest) {
        movieRepository.deleteFavorite(favoriteRequest)
    }

    fun findFavoriteMovie(key: String): LiveData<PagedList<Favorite>> = movieRepository.findFavoriteMovie(key)

    fun findFavoriteTvShow(key: String): LiveData<PagedList<Favorite>> = movieRepository.findFavoriteTvShow(key)
}
