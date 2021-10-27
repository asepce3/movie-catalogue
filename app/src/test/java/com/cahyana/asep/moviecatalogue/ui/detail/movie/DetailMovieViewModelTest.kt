package com.cahyana.asep.moviecatalogue.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cahyana.asep.moviecatalogue.data.MovieDetail
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
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
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieWithGenre>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
    }

    @Test
    fun getMovie() {
        val mDetail = Resource.success(DummyItems.getDummyMovieWithGenre()[0])
        val liveDataMovieDetail = MutableLiveData<Resource<MovieWithGenre>>()
        liveDataMovieDetail.value = mDetail

        `when`(mDetail.data?.movie?.id?.let { movieRepository.getMovieById(it) }).thenReturn(liveDataMovieDetail)
        mDetail.data?.movie?.id?.let { viewModel.setSelectedMovie(it) }
        val movie = viewModel.getMovie().value
        mDetail.data?.movie?.id?.let { verify(movieRepository).getMovieById(it) }
        assertNotNull(movie)
        assertEquals(mDetail, movie)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(mDetail)
    }
}