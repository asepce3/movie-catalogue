package com.cahyana.asep.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cahyana.asep.moviecatalogue.data.source.remote.api.ApiConfig
import com.cahyana.asep.moviecatalogue.data.source.remote.response.*
import com.cahyana.asep.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor() {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResultsItem>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResultsItem>>>()
        val callMovies = ApiConfig.getApiService().getMovies()
        callMovies.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { resultMovies.value = ApiResponse.success(it.results) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { resultMovies.value = ApiResponse.error(it, arrayListOf()) }
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovies
    }

    fun findMovies(key: String): LiveData<ApiResponse<List<MovieResultsItem>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResultsItem>>>()
        val callMovies = ApiConfig.getApiService().findMovies(key)
        callMovies.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { resultMovies.value = ApiResponse.success(it.results) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { resultMovies.value = ApiResponse.error(it, arrayListOf()) }
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovies
    }

    fun getMovieById(id: String): LiveData<ApiResponse<MovieDetailResponse?>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieDetailResponse?>>()
        val callMovie = ApiConfig.getApiService().getMovieById(id)
        callMovie.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { resultMovie.value = ApiResponse.success(it) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { resultMovie.value = ApiResponse.error(it, null) }
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<List<TvResultsItem>>> {
        EspressoIdlingResource.increment()
        val tvResults = MutableLiveData<ApiResponse<List<TvResultsItem>>>()
        val callTvShow = ApiConfig.getApiService().getTvShows()
        callTvShow.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { tvResults.value = ApiResponse.success(it) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { tvResults.value = ApiResponse.error(it, arrayListOf()) }
                EspressoIdlingResource.decrement()
            }
        })
        return  tvResults
    }

    fun findTvShow(key: String): LiveData<ApiResponse<List<TvResultsItem>>> {
        EspressoIdlingResource.increment()
        val tvResults = MutableLiveData<ApiResponse<List<TvResultsItem>>>()
        val callTvShow = ApiConfig.getApiService().findTvShow(key)
        callTvShow.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { tvResults.value = ApiResponse.success(it) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { tvResults.value = ApiResponse.error(it, arrayListOf()) }
                EspressoIdlingResource.decrement()
            }
        })
        return  tvResults
    }

    fun getTvShowById(id: String): LiveData<ApiResponse<TvDetailResponse?>> {
        EspressoIdlingResource.increment()
        val tvResult = MutableLiveData<ApiResponse<TvDetailResponse?>>()
        val callTvShow = ApiConfig.getApiService().getTvShowById(id)
        callTvShow.enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(
                call: Call<TvDetailResponse>,
                response: Response<TvDetailResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { tvResult.value = ApiResponse.success(it) }
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                logError(t.message)
                t.message?.let { tvResult.value = ApiResponse.error(it, null) }
                EspressoIdlingResource.decrement()
            }
        })

        return tvResult
    }

    private fun logError(msg: String?) {
        Log.e(RemoteDataSource::class.java.simpleName, msg ?: "")
    }
}