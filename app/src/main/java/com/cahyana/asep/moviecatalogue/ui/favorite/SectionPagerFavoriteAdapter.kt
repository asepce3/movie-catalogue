package com.cahyana.asep.moviecatalogue.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.ui.favorite.sub.FavMovieFragment
import com.cahyana.asep.moviecatalogue.ui.favorite.sub.FavTvShowFragment

class SectionPagerFavoriteAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.fav_movies, R.string.fav_tv_show)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FavMovieFragment()
            1 -> FavTvShowFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.getString(TAB_TITLES[position])
    }
}