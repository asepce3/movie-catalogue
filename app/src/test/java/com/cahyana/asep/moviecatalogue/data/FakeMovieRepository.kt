package com.cahyana.asep.moviecatalogue.data

import com.cahyana.asep.moviecatalogue.data.source.MovieDataSource

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cahyana.asep.moviecatalogue.data.source.NetworkBoundResource
import com.cahyana.asep.moviecatalogue.data.source.local.LocalDataSource
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.data.source.remote.ApiResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.RemoteDataSource
import com.cahyana.asep.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.response.MovieResultsItem
import com.cahyana.asep.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.cahyana.asep.moviecatalogue.data.source.remote.response.TvResultsItem
import com.cahyana.asep.moviecatalogue.utils.AppExecutors
import com.cahyana.asep.moviecatalogue.vo.Resource

class FakeMovieRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): MovieDataSource {

    override fun getAllMovies(): LiveData<Resource<PagedList<Movie>>> {
        return object : NetworkBoundResource<PagedList<Movie>, List<MovieResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<Movie>> {
                val config = getConfig()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResultsItem>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResultsItem>) {
                val movies = arrayListOf<Movie>()
                for (d in data) {
                    val movie = Movie(
                        d.id.toString(),
                        d.title ?: "",
                        d.releaseDate ?: "",
                        d.voteAverage ?: 0.0,
                        d.overview ?: "",
                        d.posterPath ?: "",
                        false
                    )
                    movies.add(movie)
                }
                localDataSource.saveMovieList(movies)
            }
        }.asLiveData()
    }

    override fun findMovies(key: String): LiveData<Resource<PagedList<Movie>>> {
        return object : NetworkBoundResource<PagedList<Movie>, List<MovieResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<Movie>> {
                val config = getConfig()
                return LivePagedListBuilder(localDataSource.findMovies(key), config).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResultsItem>>> =
                remoteDataSource.findMovies(key)

            override fun saveCallResult(data: List<MovieResultsItem>) {
                val movies = arrayListOf<Movie>()
                for (d in data) {
                    val movie = Movie(
                        d.id.toString(),
                        d.title ?: "",
                        d.releaseDate ?: "",
                        d.voteAverage ?: 0.0,
                        d.overview ?: "",
                        d.posterPath ?: "",
                        false
                    )
                    movies.add(movie)
                }
                localDataSource.saveMovieList(movies)
            }
        }.asLiveData()
    }

    override fun getMovieById(id: String): LiveData<Resource<MovieWithGenre>> {
        return object : NetworkBoundResource<MovieWithGenre, MovieDetailResponse?>(appExecutors) {

            override fun loadFromDB(): LiveData<MovieWithGenre> =
                localDataSource.getDetailMovieById(id)

            override fun shouldFetch(data: MovieWithGenre?): Boolean =
                data == null || !data.movie.detail

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse?>> =
                remoteDataSource.getMovieById(id)

            override fun saveCallResult(data: MovieDetailResponse?) {
                if (data != null) {
                    val genres = arrayListOf<Genre>()
                    val tempGenre = data.genres
                    if (tempGenre.isNotEmpty()) {
                        tempGenre.forEach {
                            genres.add(Genre(it.id.toString(), it.name))
                        }
                    }
                    val movieDetail = MovieDetail(
                        data.id.toString(),
                        data.title,
                        data.releaseDate,
                        data.voteAverage,
                        genres,
                        data.runtime,
                        data.overview,
                        data.posterPath
                    )
                    localDataSource.saveDetailMovie(movieDetail)
                }
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShow>>> {

        return object : NetworkBoundResource<PagedList<TvShow>, List<TvResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShow>> {
                val config = getConfig()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvResultsItem>>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: List<TvResultsItem>) {
                val tvShows = arrayListOf<TvShow>()
                data.forEach {
                    tvShows.add(
                        TvShow(
                            it.id.toString(),
                            it.name ?: "",
                            it.voteAverage ?: 0.0,
                            it.firstAirDate ?: "",
                            it.overview ?: "",
                            it.posterPath ?: ""
                        )
                    )
                }
                localDataSource.saveTvShowList(tvShows)
            }
        }.asLiveData()
    }

    override fun findTvShow(key: String): LiveData<Resource<PagedList<TvShow>>> {
        return object : NetworkBoundResource<PagedList<TvShow>, List<TvResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShow>> {
                val config = getConfig()
                return LivePagedListBuilder(localDataSource.findTvShow(key), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvResultsItem>>> =
                remoteDataSource.findTvShow(key)

            override fun saveCallResult(data: List<TvResultsItem>) {
                val tvShows = arrayListOf<TvShow>()
                data.forEach {
                    tvShows.add(
                        TvShow(
                            it.id.toString(),
                            it.name ?: "",
                            it.voteAverage ?: 0.0,
                            it.firstAirDate ?: "",
                            it.overview ?: "",
                            it.posterPath ?: ""
                        )
                    )
                }
                localDataSource.saveTvShowList(tvShows)
            }
        }.asLiveData()
    }

    override fun getTvShowById(id: String): LiveData<Resource<TvShowWithGenre>> {
        return object : NetworkBoundResource<TvShowWithGenre, TvDetailResponse?>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowWithGenre> =
                localDataSource.getDetailTvShowById(id)

            override fun shouldFetch(data: TvShowWithGenre?): Boolean =
                data == null || !data.tvShow.detail

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse?>> =
                remoteDataSource.getTvShowById(id)

            override fun saveCallResult(data: TvDetailResponse?) {
                if (data != null) {
                    val genres = arrayListOf<Genre>()
                    data.genres.forEach {
                        genres.add(Genre(it.id.toString(), it.name))
                    }
                    val detail = TvShowDetail(
                        data.id.toString(),
                        data.name,
                        data.voteAverage,
                        data.firstAirDate,
                        data.numberOfSeasons,
                        data.numberOfEpisodes,
                        genres,
                        data.overview,
                        data.posterPath
                    )
                    localDataSource.saveDetailTvShow(detail)
                }
            }

        }.asLiveData()
    }

    override fun getAllFavoriteMovies(): LiveData<PagedList<Favorite>> {
        return LivePagedListBuilder(localDataSource.getAllFavoriteMovies(), getConfig()).build()
    }

    override fun getAllFavoriteTvShows(): LiveData<PagedList<Favorite>> {
        return LivePagedListBuilder(localDataSource.getAllFavoriteTvShows(), getConfig()).build()
    }

    override fun findFavoriteMovie(key: String): LiveData<PagedList<Favorite>> {
        return LivePagedListBuilder(localDataSource.findFavoriteMovie(key), getConfig()).build()
    }

    override fun findFavoriteTvShow(key: String): LiveData<PagedList<Favorite>> {
        return LivePagedListBuilder(localDataSource.findFavoriteTvShow(key), getConfig()).build()
    }

    private fun getConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
    }

    override fun saveFavorite(favorite: FavoriteRequest) {
        appExecutors.diskIO().execute {
            localDataSource.saveFavorite(favorite)
        }
    }

    override fun deleteFavorite(favorite: FavoriteRequest) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(favorite)
        }
    }
}