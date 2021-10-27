package com.cahyana.asep.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.databinding.FragmentTvShowBinding
import com.cahyana.asep.moviecatalogue.ui.home.HomeViewModel
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback
import com.cahyana.asep.moviecatalogue.viewmodel.ViewModelFactory
import com.cahyana.asep.moviecatalogue.vo.Status
import com.google.android.material.snackbar.Snackbar

class TvShowFragment : Fragment(), SelectCallback {

    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding
    private var adapterList: TvShowAdapter? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter1: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(inflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
            val adapter = TvShowAdapter(this)
            adapterList = adapter
            this.adapter1 = adapter

            observMovies()

            with(fragmentTvShowBinding.rvTvshow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)
        (menu.findItem(R.id.search).actionView as SearchView).setOnQueryTextListener(queryListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.option_refresh -> observMovies()
        }
        return true
    }

    override fun onSave(favorite: FavoriteRequest) {
        viewModel.saveFavorite(favorite)
    }

    override fun onDelete(favorite: FavoriteRequest) {
        viewModel.deleteFavorite(favorite)
    }

    private val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrEmpty())
                search(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }

    private fun observMovies() {
        viewModel.getAllTvShows().observe(viewLifecycleOwner, { tvShows ->
            when(tvShows.status) {
                Status.LOADING -> fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    fragmentTvShowBinding.progressBar.visibility = View.GONE
                    adapter1.submitList(tvShows.data)
                }
                Status.ERROR -> {
                    fragmentTvShowBinding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, tvShows.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun search(key: String) {
        viewModel.findTvShow(key).observe(viewLifecycleOwner, { tvShows ->
            when(tvShows.status) {
                Status.LOADING -> fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    fragmentTvShowBinding.progressBar.visibility = View.GONE

                    val data = tvShows.data
                    if (data != null && data.isEmpty()) {
                        view?.let { Snackbar.make(it, String.format(getText(R.string.find_not_found).toString(), key), Snackbar.LENGTH_LONG).show() }
                    } else {
                        adapter1.submitList(tvShows.data)
                    }
                }
                Status.ERROR -> {
                    fragmentTvShowBinding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, tvShows.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}