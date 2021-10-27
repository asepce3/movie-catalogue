package com.cahyana.asep.moviecatalogue.ui.detail.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cahyana.asep.moviecatalogue.data.TvShowDetail
import com.cahyana.asep.moviecatalogue.data.source.MovieRepository
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
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
class DetailTvShowViewModelTest {

    private lateinit var viewModel: DetailTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvShowWithGenre>>

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShow() {
        val mTvDetail = Resource.success(DummyItems.getDummyTvShowWithGenre()[0])
        val liveDataTvDetail = MutableLiveData<Resource<TvShowWithGenre>>()
        liveDataTvDetail.value = mTvDetail

        `when`(mTvDetail.data?.tvShow?.id?.let { movieRepository.getTvShowById(it) }).thenReturn(liveDataTvDetail)
        mTvDetail.data?.tvShow?.id?.let { viewModel.setSelectedTvShow(it) }
        val tvShow = viewModel.getTvShow().value
        mTvDetail.data?.tvShow?.id?.let { verify(movieRepository).getTvShowById(it) }
        assertNotNull(tvShow)
        assertEquals(mTvDetail, tvShow)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(mTvDetail)
    }
}