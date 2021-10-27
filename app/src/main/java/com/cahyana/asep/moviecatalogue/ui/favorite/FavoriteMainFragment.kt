package com.cahyana.asep.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cahyana.asep.moviecatalogue.databinding.FragmentFavoriteMainBinding


class FavoriteMainFragment : Fragment() {
    private lateinit var fragmentFavoriteMainBinding: FragmentFavoriteMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteMainBinding = FragmentFavoriteMainBinding.inflate(inflater, container, false)
        return fragmentFavoriteMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (activity != null) {
            val sectionAdapter = SectionPagerFavoriteAdapter(requireContext(), childFragmentManager)
            fragmentFavoriteMainBinding.viewPager.adapter = sectionAdapter
            fragmentFavoriteMainBinding.tabs.setupWithViewPager(fragmentFavoriteMainBinding.viewPager)

        }
    }
}