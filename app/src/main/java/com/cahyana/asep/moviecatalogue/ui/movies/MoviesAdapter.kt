package com.cahyana.asep.moviecatalogue.ui.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.config.Config
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.data.FavoriteType
import com.cahyana.asep.moviecatalogue.data.Movie
import com.cahyana.asep.moviecatalogue.databinding.ItemsMovieBinding
import com.cahyana.asep.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback
import com.cahyana.asep.moviecatalogue.utils.ResourceUtils

class MoviesAdapter(private val selectCallback: SelectCallback) :
    PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val ratingDivider = 2f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null)
            holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                val context = itemView.context

                populateView(movie)
                changeBtnStatus(movie, context)

                btnFavorite.setOnClickListener {
                    markAsFavorite(movie, context)
                }
                itemView.setOnClickListener {
                    launchShareIntent(context, movie)
                }
            }
        }

        private fun ItemsMovieBinding.markAsFavorite(
            movie: Movie,
            context: Context
        ) {
            if (!movie.favorite) {
                selectCallback.onSave(FavoriteRequest(movie.id, FavoriteType.MOVIE))
                btnFavorite.setImageDrawable(
                    ResourceUtils.btnOn(context)
                )
            } else {
                selectCallback.onDelete(FavoriteRequest(movie.id, FavoriteType.MOVIE))
                btnFavorite.setImageDrawable(
                    ResourceUtils.btnOff(context)
                )
            }
        }

        private fun launchShareIntent(
            context: Context?,
            movie: Movie
        ) {
            val intentDetailMovie = Intent(context, DetailMovieActivity::class.java)
            intentDetailMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.id)
            itemView.context.startActivity(intentDetailMovie)
        }

        private fun ItemsMovieBinding.populateView(movie: Movie) {
            tvMovieTitle.text = movie.title
            tvMovieDesc.text = movie.overview
            ratingBar.rating = movie.score.toFloat() / ratingDivider
            tvMovieRelease.text = movie.release

            Glide.with(itemView.context)
                .load(String.format(Config.IMAGE_PATH, movie.imagePath))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_replay))
                .error(R.drawable.ic_panorama)
                .into(imgPoster)
        }

        private fun ItemsMovieBinding.changeBtnStatus(
            movie: Movie,
            context: Context
        ) {
            if (movie.favorite)
                btnFavorite.setImageDrawable(ResourceUtils.btnOn(context))
            else
                btnFavorite.setImageDrawable(ResourceUtils.btnOff(context))
        }
    }
}
