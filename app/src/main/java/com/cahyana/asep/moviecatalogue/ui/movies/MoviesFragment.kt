package com.cahyana.asep.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.databinding.FragmentMoviesBinding
import com.cahyana.asep.moviecatalogue.ui.home.HomeViewModel
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback
import com.cahyana.asep.moviecatalogue.viewmodel.ViewModelFactory
import com.cahyana.asep.moviecatalogue.vo.Status
import com.google.android.material.snackbar.Snackbar

class MoviesFragment : Fragment(), SelectCallback {

    private lateinit var fragmentMoviesBinding: FragmentMoviesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var moviesAdapter1: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMoviesBinding = FragmentMoviesBinding
            .inflate(inflater, container, false)
        return fragmentMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
            val moviesAdapter = MoviesAdapter(this)
            this.moviesAdapter1 = moviesAdapter

            getAllMovies()

            with(fragmentMoviesBinding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }

    override fun onSave(favorite: FavoriteRequest) {
        viewModel.saveFavorite(favorite)
    }

    override fun onDelete(favorite: FavoriteRequest) {
        viewModel.deleteFavorite(favorite)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)

        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.setOnQueryTextListener(queryListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.option_refresh -> {
                getAllMovies()
            }
        }
        return true
    }

    private fun getAllMovies() {
        viewModel.getAllMovies().observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when(movies.status) {
                    Status.LOADING -> fragmentMoviesBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        fragmentMoviesBinding.progressBar.visibility = View.GONE
                        moviesAdapter1.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        fragmentMoviesBinding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrBlank())
                search(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }

    private fun search(key: String) {
        viewModel.findMovies(key).observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when(movies.status) {
                    Status.LOADING -> fragmentMoviesBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        fragmentMoviesBinding.progressBar.visibility = View.GONE

                        val data = movies.data
                        if (data != null && data.isEmpty()) {
                            view?.let { Snackbar.make(it, String.format(getText(R.string.find_not_found).toString(), key), Snackbar.LENGTH_LONG).show() }
                        } else {
                            moviesAdapter1.submitList(movies.data)
                        }
                    }
                    Status.ERROR -> {
                        fragmentMoviesBinding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }
}