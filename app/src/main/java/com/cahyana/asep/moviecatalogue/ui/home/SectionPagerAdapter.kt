package com.cahyana.asep.moviecatalogue.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.ui.favorite.FavoriteMainFragment
import com.cahyana.asep.moviecatalogue.ui.movies.MoviesFragment
import com.cahyana.asep.moviecatalogue.ui.tvshow.TvShowFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_show, R.string.my_favorite)
    }
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MoviesFragment()
            1 -> TvShowFragment()
            2 -> FavoriteMainFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.getString(TAB_TITLES[position])
    }
}
