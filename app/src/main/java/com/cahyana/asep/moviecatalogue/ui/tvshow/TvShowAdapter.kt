package com.cahyana.asep.moviecatalogue.ui.tvshow

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
import com.cahyana.asep.moviecatalogue.data.TvShow
import com.cahyana.asep.moviecatalogue.databinding.ItemsTvshowBinding
import com.cahyana.asep.moviecatalogue.ui.detail.tvshow.DetailTvShowActivity
import com.cahyana.asep.moviecatalogue.ui.home.SelectCallback
import com.cahyana.asep.moviecatalogue.utils.ResourceUtils

class TvShowAdapter(private val selectCallback: SelectCallback) :
    PagedListAdapter<TvShow, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow> () {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem
        }
    }

    private val ratingDivider = 2f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null)
            holder.bind(tvShow)
    }

    inner class TvShowViewHolder(private val binding: ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(tvShow: TvShow) {
            with(binding) {
                val context = itemView.context
                populateView(tvShow)
                changeBtnStatus(tvShow, context)

                btnFavorite.setOnClickListener {
                    markAsFavorite(tvShow, context)
                }

                itemView.setOnClickListener {
                    launchShareIntent(context, tvShow)
                }
            }
        }

        private fun launchShareIntent(
            context: Context?,
            tvShow: TvShow
        ) {
            val intentTvShow = Intent(context, DetailTvShowActivity::class.java)
            intentTvShow.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow.id)
            itemView.context.startActivity(intentTvShow)
        }

        private fun ItemsTvshowBinding.markAsFavorite(
            tvShow: TvShow,
            context: Context
        ) {
            if (!tvShow.favorite) {
                selectCallback.onSave(FavoriteRequest(tvShow.id, FavoriteType.TVSHOW))
                btnFavorite.setImageDrawable(ResourceUtils.btnOn(context))
            } else {
                selectCallback.onDelete(FavoriteRequest(tvShow.id, FavoriteType.TVSHOW))
                btnFavorite.setImageDrawable(ResourceUtils.btnOff(context))
            }
        }

        private fun ItemsTvshowBinding.populateView(tvShow: TvShow) {
            tvShowTitle.text = tvShow.title
            tvShowDesc.text = tvShow.overview
            tvShowRelease.text = tvShow.year
            ratingBarShow.rating = tvShow.score.toFloat() / ratingDivider

            Glide.with(imgShowPoster.context)
                .load(String.format(Config.IMAGE_PATH, tvShow.imagePath))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_replay))
                .error(R.drawable.ic_panorama)
                .into(imgShowPoster)
        }

        private fun ItemsTvshowBinding.changeBtnStatus(
            tvShow: TvShow,
            context: Context
        ) {
            if (tvShow.favorite)
                btnFavorite.setImageDrawable(ResourceUtils.btnOn(context))
            else
                btnFavorite.setImageDrawable(ResourceUtils.btnOff(context))
        }
    }
}