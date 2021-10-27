package com.cahyana.asep.moviecatalogue.data.source.remote.api

import com.cahyana.asep.moviecatalogue.BuildConfig
import com.cahyana.asep.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.response.MovieResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.*

private const val API_KEY = BuildConfig.API_KEY

interface ApiService {

    /**
     * Get movie list
     */
    @GET("movie/popular?api_key=${API_KEY}&language=en-US&page=1")
    fun getMovies(): Call<MovieResponse>

    /**
     * Get movie detail
     */
    @GET("movie/{id}?api_key=${API_KEY}&language=en-US")
    fun getMovieById(
        @Path("id") id: String
    ): Call<MovieDetailResponse>

    /**
     * Find movie by title
     */
    @GET("search/movie?api_key=${API_KEY}&language=en-US&page=1&include_adult=false")
    fun findMovies(@Query("query") key: String) : Call<MovieResponse>

    /**
     * Get tv show list
     */
    @GET("tv/popular?api_key=${API_KEY}&language=en-US&page=1")
    fun getTvShows(): Call<TvResponse>

    /**
     * Get tv show detail
     */
    @GET("tv/{id}?api_key=${API_KEY}&language=en-US")
    fun getTvShowById(
        @Path("id") id: String
    ): Call<TvDetailResponse>

    /**
     * Find tv show by title
     */
    @GET("search/tv?api_key=${API_KEY}&language=en-US&page=1&include_adult=false")
    fun findTvShow(@Query("query") key: String): Call<TvResponse>
}