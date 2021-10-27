package com.cahyana.asep.moviecatalogue.ui.favorite.sub

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
import com.cahyana.asep.moviecatalogue.data.Favorite
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.data.FavoriteType
import com.cahyana.asep.moviecatalogue.databinding.ItemsFavMovieBinding
import com.cahyana.asep.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.cahyana.asep.moviecatalogue.ui.detail.tvshow.DetailTvShowActivity
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback

class FavMovieAdapter(private val selectCallback: SelectCallback) :
    PagedListAdapter<Favorite, FavMovieAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                oldItem == newItem
        }
    }

    private val ratingDivider = 2f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemsFavoriteBinding = ItemsFavMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemsFavoriteBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        if (favorite != null) {
            holder.bind(favorite)
        }
    }

    fun getSwipedData(swipedPosition: Int): Favorite? = getItem(swipedPosition)

    inner class FavoriteViewHolder(private val binding: ItemsFavMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {
            with(binding) {
                val context = itemView.context
                pupulateView(favorite)

                btnFavorite.setOnClickListener {
                    removeThisFavorite(favorite)
                }

                itemView.setOnClickListener {
                    launchShareIntent(favorite, context)
                }
            }
        }

        private fun removeThisFavorite(favorite: Favorite) {
            selectCallback.onDelete(FavoriteRequest(favorite.id, favorite.type))
        }

        private fun launchShareIntent(
            favorite: Favorite,
            context: Context?
        ) {
            when (favorite.type) {
                FavoriteType.MOVIE -> {
                    val intentDetailMovie = Intent(context, DetailMovieActivity::class.java)
                    intentDetailMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, favorite.id)
                    itemView.context.startActivity(intentDetailMovie)
                }
                FavoriteType.TVSHOW -> {
                    val intentDetailTvshow = Intent(context, DetailTvShowActivity::class.java)
                    intentDetailTvshow.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, favorite.id)
                    itemView.context.startActivity(intentDetailTvshow)
                }
            }
        }

        private fun ItemsFavMovieBinding.pupulateView(favorite: Favorite) {
            tvFavoriteTitle.text = favorite.title
            tvFavoriteDesc.text = favorite.overview
            tvFavoriteType.text = favorite.type.name
            ratingBarFavorite.rating = favorite.score.toFloat() / ratingDivider

            Glide.with(imgFavoritePoster.context)
                .load(String.format(Config.IMAGE_PATH, favorite.imagePath))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_replay))
                .error(R.drawable.ic_panorama)
                .into(imgFavoritePoster)
        }
    }
}