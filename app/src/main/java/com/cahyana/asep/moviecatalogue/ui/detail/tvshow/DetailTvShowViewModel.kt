package com.cahyana.asep.moviecatalogue.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.vo.Resource

class DetailTvShowViewModel(private val mMovieRepository: MovieRepository) : ViewModel() {

    private lateinit var tvShowId: String

    fun setSelectedTvShow(tvShowId: String) {
        this.tvShowId = tvShowId
    }

    fun getTvShow(): LiveData<Resource<TvShowWithGenre>> {
        return mMovieRepository.getTvShowById(tvShowId)
    }

    fun saveFavorite(request: FavoriteRequest) {
        mMovieRepository.saveFavorite(request)
    }

    fun deleteFavorite(request: FavoriteRequest) {
        mMovieRepository.deleteFavorite(request)
    }
}