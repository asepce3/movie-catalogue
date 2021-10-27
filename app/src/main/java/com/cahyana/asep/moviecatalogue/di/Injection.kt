package com.cahyana.asep.moviecatalogue.di

import android.content.Context
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.data.source.local.LocalDataSource
import com.cahyana.asep.moviecatalogue.data.source.local.room.MovieDatabase
import com.cahyana.asep.moviecatalogue.data.source.remote.RemoteDataSource
import com.cahyana.asep.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val movieDatabase = MovieDatabase.getInstance(context)
        val movieDao = movieDatabase.movieDao()
        val tvShowDao = movieDatabase.tvShowDao()
        val favoriteDao = movieDatabase.favoriteDao()
        val localDataSource = LocalDataSource.getInstance(movieDao, tvShowDao, favoriteDao)
        val remoteDataSource = RemoteDataSource.getInstance()
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(localDataSource, remoteDataSource, appExecutors)
    }
}