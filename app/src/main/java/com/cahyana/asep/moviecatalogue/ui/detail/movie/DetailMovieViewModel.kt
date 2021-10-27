package com.cahyana.asep.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.vo.Resource

class DetailMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var movieId: String

    fun setSelectedMovie(movieId: String) {
        this.movieId = movieId
    }

    fun getMovie(): LiveData<Resource<MovieWithGenre>> {
        return movieRepository.getMovieById(movieId)
    }

    fun saveFavorite(request: FavoriteRequest) {
        movieRepository.saveFavorite(request)
    }

    fun deleteFavorite(request: FavoriteRequest) {
        movieRepository.deleteFavorite(request)
    }
}