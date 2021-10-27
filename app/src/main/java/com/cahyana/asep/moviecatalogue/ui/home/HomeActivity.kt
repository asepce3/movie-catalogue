package com.cahyana.asep.moviecatalogue.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val actionbarElevation = 10f
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        bottomNavigation = activityHomeBinding.buttomNavigation
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        activityHomeBinding.viewPager.adapter = sectionPagerAdapter
        viewPager = activityHomeBinding.viewPager
        activityHomeBinding.buttomNavigation.setOnNavigationItemSelectedListener(this)

        viewPager.addOnPageChangeListener(pageChangeListener)

        supportActionBar?.elevation = actionbarElevation
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.tab_movie -> viewPager.currentItem = 0
            R.id.tab_tvshow -> viewPager.currentItem = 1
            R.id.tab_favorite -> viewPager.currentItem = 2
        }
        return true
    }

    private val pageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            when(position) {
                0 -> bottomNavigation.menu.findItem(R.id.tab_movie).isChecked = true
                1 -> bottomNavigation.menu.findItem(R.id.tab_tvshow).isChecked = true
                2 -> bottomNavigation.menu.findItem(R.id.tab_favorite).isChecked = true
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }
}