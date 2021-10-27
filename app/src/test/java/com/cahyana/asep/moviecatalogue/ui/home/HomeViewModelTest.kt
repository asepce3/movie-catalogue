package com.cahyana.asep.moviecatalogue.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.utils.DummyItems
import com.cahyana.asep.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var request: FavoriteRequest
    private val dummyMovies = DummyItems.getDummyFavorite()
    private val key = "Naruto"



    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerListMovie: Observer<Resource<PagedList<Movie>>>

    @Mock
    private lateinit var observerListTvShow: Observer<Resource<PagedList<TvShow>>>

    @Mock
    private lateinit var observerListFavorite: Observer<PagedList<Favorite>>

    @Mock
    private lateinit var pagedListMovie: PagedList<Movie>

    @Mock
    private lateinit var pagedListTvShow: PagedList<TvShow>

    @Mock
    private lateinit var pagedListFavorite: PagedList<Favorite>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(movieRepository)
        val movie = DummyItems.getMovie()
        request = FavoriteRequest(movie.id, FavoriteType.MOVIE)
    }

    @Test
    fun getAllMovies() {
        val mMovies = Resource.success(pagedListMovie)
        `when`(mMovies.data?.size).thenReturn(1)
        val liveData = MutableLiveData<Resource<PagedList<Movie>>>()
        liveData.value = mMovies

        `when`(movieRepository.getAllMovies()).thenReturn(liveData)
        val movies = viewModel.getAllMovies().value?.data
        verify(movieRepository).getAllMovies()
        assertNotNull(movies)
        assertEquals(1, movies?.size)

        viewModel.getAllMovies().observeForever(observerListMovie)
        verify(observerListMovie).onChanged(mMovies)
    }

    @Test
    fun getAllTvShows() {
        val mTvShows = Resource.success(pagedListTvShow)
        `when`(mTvShows.data?.size).thenReturn(1)
        val liveData = MutableLiveData<Resource<PagedList<TvShow>>>()
        liveData.value = mTvShows

        `when`(movieRepository.getAllTvShows()).thenReturn(liveData)
        val tvShows = viewModel.getAllTvShows().value?.data
        verify(movieRepository).getAllTvShows()
        assertNotNull(tvShows)
        assertEquals(1, tvShows?.size)

        viewModel.getAllTvShows().observeForever(observerListTvShow)
        verify(observerListTvShow).onChanged(mTvShows)
    }

    @Test
    fun getFavoriteMovies() {
        `when`(pagedListFavorite.size).thenReturn(1)
        val liveData = MutableLiveData<PagedList<Favorite>>()
        liveData.value = pagedListFavorite

        `when`(movieRepository.getAllFavoriteMovies()).thenReturn(liveData)
        val favorites = viewModel.getAllFavoriteMovies().value
        verify(movieRepository).getAllFavoriteMovies()
        assertNotNull(favorites)
        assertEquals(1, favorites?.size)
    }

    @Test
    fun findTvShows() {
        val mTvShows = Resource.success(pagedListTvShow)
        `when`(mTvShows.data?.size).thenReturn(1)
        val liveData = MutableLiveData<Resource<PagedList<TvShow>>>()
        liveData.value = mTvShows

        `when`(movieRepository.findTvShow(key)).thenReturn(liveData)
        val tvShows = viewModel.findTvShow(key).value?.data
        verify(movieRepository).findTvShow(key)
        assertNotNull(tvShows)
        assertEquals(1, tvShows?.size)

        viewModel.findTvShow(key).observeForever(observerListTvShow)
        verify(observerListTvShow).onChanged(mTvShows)
    }

    @Test
    fun findMovies() {
        val mMovies = Resource.success(pagedListMovie)
        `when`(mMovies.data?.size).thenReturn(1)
        val liveData = MutableLiveData<Resource<PagedList<Movie>>>()
        liveData.value = mMovies

        `when`(movieRepository.findMovies(key)).thenReturn(liveData)
        val movies = viewModel.findMovies(key).value?.data
        verify(movieRepository).findMovies(key)
        assertNotNull(movies)
        assertEquals(1, movies?.size)

        viewModel.findMovies(key).observeForever(observerListMovie)
        verify(observerListMovie).onChanged(mMovies)
    }

    @Test
    fun getFavoriteTvShows() {
        `when`(pagedListFavorite.size).thenReturn(1)
        val liveData = MutableLiveData<PagedList<Favorite>>()
        liveData.value = pagedListFavorite

        `when`(movieRepository.getAllFavoriteTvShows()).thenReturn(liveData)
        val favorites = viewModel.getAllFavoriteTvShows().value
        verify(movieRepository).getAllFavoriteTvShows()
        assertNotNull(favorites)
        assertEquals(1, favorites?.size)
    }

    @Test
    fun findFavoriteMovie() {
        `when`(pagedListFavorite.size).thenReturn(1)
        val liveData = MutableLiveData<PagedList<Favorite>>()
        liveData.value = pagedListFavorite

        `when`(movieRepository.findFavoriteMovie(key)).thenReturn(liveData)
        val favorites = viewModel.findFavoriteMovie(key).value
        verify(movieRepository).findFavoriteMovie(key)
        assertNotNull(favorites)
        assertEquals(1, favorites?.size)
    }

    @Test
    fun findFavoriteTvShow() {
        `when`(pagedListFavorite.size).thenReturn(1)
        val liveData = MutableLiveData<PagedList<Favorite>>()
        liveData.value = pagedListFavorite

        `when`(movieRepository.findFavoriteTvShow(key)).thenReturn(liveData)
        val favorites = viewModel.findFavoriteTvShow(key).value
        verify(movieRepository).findFavoriteTvShow(key)
        assertNotNull(favorites)
        assertEquals(1, favorites?.size)
    }

    @Test
    fun saveFavorite() {
        viewModel.saveFavorite(request)
        verify(movieRepository).saveFavorite(request)
    }

    @Test
    fun deleteFavorite() {
        viewModel.deleteFavorite(request)
        verify(movieRepository).deleteFavorite(request)
    }
}