package com.cahyana.asep.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cahyana.asep.moviecatalogue.data.source.local.LocalDataSource
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.data.source.remote.RemoteDataSource
import com.cahyana.asep.moviecatalogue.utils.AppExecutors
import com.cahyana.asep.moviecatalogue.utils.DummyItems
import com.cahyana.asep.moviecatalogue.utils.LiveDataTestUtil
import com.cahyana.asep.moviecatalogue.utils.PagedListUtil
import com.cahyana.asep.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val local = mock(LocalDataSource::class.java)
    private val remote = mock(RemoteDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(local, remote, appExecutors)

    private val movieResponses = DummyItems.getMovieResponses()
    private val movieResultItems = movieResponses[0].results
    private val tvShowResponses = DummyItems.getTvResponses()
    private val tvResultItems = tvShowResponses[0].results
    private val movieWithGenre = DummyItems.getDummyMovieWithGenre()
    private val movieId = movieWithGenre[0].movie.id
    private val tvShowWithGenre = DummyItems.getDummyTvShowWithGenre()
    private val tvId = tvShowWithGenre[0].tvShow.id
    private val dummyFavorite = DummyItems.getDummyFavorite()
    private val dummyMovies = DummyItems.getDummyMovie()
    private val dummyTvShows = DummyItems.getDummyTvShow()


    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movies = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
        verify(local).getAllMovies()
        assertNotNull(movies.data)
        assertEquals(movieResultItems.size.toLong(), movies.data?.size?.toLong())
    }

    @Test
    fun findMovie() {

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        `when`(local.findMovies(movieId)).thenReturn(dataSourceFactory)
        movieRepository.findMovies(movieId)

        val movies = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
        verify(local).findMovies(movieId)
        assertNotNull(movies.data)
        assertEquals(movieResultItems.size.toLong(), movies.data?.size?.toLong())
    }

    @Test
    fun findTvShow() {

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShow>
        `when`(local.findTvShow(movieId)).thenReturn(dataSourceFactory)
        movieRepository.findTvShow(movieId)

        val tvShows = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
        verify(local).findTvShow(movieId)
        assertNotNull(tvShows.data)
        assertEquals(movieResultItems.size.toLong(), tvShows.data?.size?.toLong())
    }

    @Test
    fun findFavoriteTvShow() {

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Favorite>
        `when`(local.findFavoriteTvShow(movieId)).thenReturn(dataSourceFactory)
        movieRepository.findFavoriteTvShow(movieId)

        val tvShows = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
        verify(local).findFavoriteTvShow(movieId)
        assertNotNull(tvShows.data)
        assertEquals(movieResultItems.size.toLong(), tvShows.data?.size?.toLong())
    }

    @Test
    fun findFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Favorite>
        `when`(local.findFavoriteMovie(movieId)).thenReturn(dataSourceFactory)
        movieRepository.findFavoriteMovie(movieId)

        val tvShows = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
        verify(local).findFavoriteMovie(movieId)
        assertNotNull(tvShows.data)
        assertEquals(movieResultItems.size.toLong(), tvShows.data?.size?.toLong())
    }


    @Test
    fun getMovieById() {
        val mMovie = MutableLiveData<MovieWithGenre>()
        mMovie.value = movieWithGenre[0]
        `when`(local.getDetailMovieById(movieId)).thenReturn(mMovie)
        val movieDetail = LiveDataTestUtil.getValue(movieRepository.getMovieById(movieId))
        verify(local, atLeast(1)).getDetailMovieById(movieId)
        assertNotNull(movieDetail.data)
        assertEquals(movieId, movieDetail.data?.movie?.id)
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShow>
        `when`(local.getAllTvShow()).thenReturn(dataSourceFactory)
        movieRepository.getAllTvShows()

        val tvShows = Resource.success(PagedListUtil.mockPagedList(dummyTvShows))
        verify(local).getAllTvShow()
        assertNotNull(tvShows)
        assertEquals(tvResultItems.size.toLong(), tvShows.data?.size?.toLong())
    }

    @Test
    fun getTvShowById() {
        val mTvShow = MutableLiveData<TvShowWithGenre>()
        mTvShow.value = tvShowWithGenre[0]

        `when`(local.getDetailTvShowById(tvId)).thenReturn(mTvShow)
        val tvDetail = LiveDataTestUtil.getValue(movieRepository.getTvShowById(tvId))
        verify(local).getDetailTvShowById(eq(tvId))
        assertNotNull(tvDetail)
        assertEquals(tvId, tvDetail.data?.tvShow?.id)
    }

    @Test
    fun getAllFavoriteMovies() {
        val mFavorites = MutableLiveData<List<Favorite>>()
        mFavorites.value = dummyFavorite

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Favorite>
        `when`(local.getAllFavoriteMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllFavoriteMovies()
        val favorites = Resource.success(PagedListUtil.mockPagedList(dummyFavorite))
        verify(local).getAllFavoriteMovies()
        assertNotNull(favorites)
        assertEquals(dummyFavorite.size.toLong(), favorites.data?.size?.toLong())
    }

    @Test
    fun getAllFavoriteTvShows() {
        val mFavorites = MutableLiveData<List<Favorite>>()
        mFavorites.value = dummyFavorite

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Favorite>
        `when`(local.getAllFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getAllFavoriteTvShows()
        val favorites = Resource.success(PagedListUtil.mockPagedList(dummyFavorite))
        verify(local).getAllFavoriteTvShows()
        assertNotNull(favorites)
        assertEquals(dummyFavorite.size.toLong(), favorites.data?.size?.toLong())
    }
}
