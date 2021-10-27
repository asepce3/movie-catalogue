package com.cahyana.asep.moviecatalogue.ui.favorite.sub

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.databinding.FragmentFavMovieBinding
import com.cahyana.asep.moviecatalogue.ui.home.HomeViewModel
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback
import com.cahyana.asep.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavMovieFragment : Fragment(), SelectCallback {

    private lateinit var fragmentFavMovieBinding: FragmentFavMovieBinding
    private lateinit var adapter1: FavMovieAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavMovieBinding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return fragmentFavMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(fragmentFavMovieBinding.rvFavoriteMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
            val adapter = FavMovieAdapter(this)
            this.adapter1 = adapter

            observFavMovies()

            with(fragmentFavMovieBinding.rvFavoriteMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
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

        (menu.findItem(R.id.search).actionView as SearchView).setOnQueryTextListener(queryListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.option_refresh -> observFavMovies()
        }
        return true
    }

    private fun observFavMovies() {
        viewModel.getAllFavoriteMovies().observe(viewLifecycleOwner, { favorites ->
            if (favorites.isNotEmpty()) {
                adapter1.submitList(favorites)
            }
        })
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

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favorite = adapter1.getSwipedData(swipedPosition)
                favorite?.let { viewModel.deleteFavorite(FavoriteRequest(favorite.id, favorite.type)) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) {
                    favorite?.let { viewModel.saveFavorite(FavoriteRequest(favorite.id, favorite.type)) }
                }
                snackbar.show()
            }
        }
    })

    private fun search(key: String) {
        viewModel.findFavoriteMovie(key).observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                view?.let { it1 -> Snackbar.make(it1, String.format(getString(R.string.find_not_found), key), Snackbar.LENGTH_LONG).show() }
            } else {
                adapter1.submitList(it)
            }
        })
    }
}
