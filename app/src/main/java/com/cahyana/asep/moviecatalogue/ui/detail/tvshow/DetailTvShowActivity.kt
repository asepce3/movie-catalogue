package com.cahyana.asep.moviecatalogue.ui.detail.tvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.config.Config
import com.cahyana.asep.moviecatalogue.data.FavoriteRequest
import com.cahyana.asep.moviecatalogue.data.FavoriteType
import com.cahyana.asep.moviecatalogue.data.TvShowDetail
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.databinding.ActivityDetailTvShowBinding
import com.cahyana.asep.moviecatalogue.databinding.ContentDetailTvshowBinding
import com.cahyana.asep.moviecatalogue.utils.ResourceUtils
import com.cahyana.asep.moviecatalogue.viewmodel.ViewModelFactory
import com.cahyana.asep.moviecatalogue.vo.Status

class DetailTvShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvShowBinding
    private lateinit var detailTvShowBinding: ContentDetailTvshowBinding
    private lateinit var viewModel: DetailTvShowViewModel

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        detailTvShowBinding = binding.detailTvshow
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val tvshowId = extras.getString(EXTRA_TVSHOW)
            if (tvshowId != null) {
                viewModel.setSelectedTvShow(tvshowId)
                viewModel.getTvShow().observe(this, { tvShow ->
                    when(tvShow.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            if (tvShow.data != null)
                                populateMovie(toMovieDetail(tvShow.data))
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, tvShow.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun toMovieDetail(tvShow: TvShowWithGenre): TvShowDetail {
        return TvShowDetail(
            tvShow.tvShow.id,
            tvShow.tvShow.title,
            tvShow.tvShow.score,
            tvShow.tvShow.year,
            tvShow.tvShow.season,
            tvShow.tvShow.episode,
            tvShow.genres,
            tvShow.tvShow.overview,
            tvShow.tvShow.imagePath,
            tvShow.tvShow.favorite,
            tvShow.tvShow.detail
        )
    }

    private fun populateMovie(tvShow: TvShowDetail) {
        with(detailTvShowBinding) {
            tvTvshowTitle.text = tvShow.title
            tvSeason.text = tvShow.season.toString()
            tvTvshowDesc.text = tvShow.overview
            tvTvshowEpisode.text = tvShow.episode.toString()
            val genreTxt = arrayListOf<String>()
            tvShow.genres.forEach { genreTxt.add(it.name) }
            tvTvshowGenre.text = genreTxt.joinToString()
            tvTvshowYear.text = tvShow.year
            ratingBar.rating = tvShow.score.toFloat() / 2f
            Glide.with(imagePoster.context)
                .load(String.format(Config.IMAGE_PATH, tvShow.imagePath))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_replay))
                .error(R.drawable.ic_panorama)
                .into(imagePoster)

            changeBtnStatus(tvShow)

            btnFavorite.setOnClickListener {
                favoriteAction(tvShow)
            }

            btnShare.setOnClickListener { share() }
        }
    }

    private fun ContentDetailTvshowBinding.favoriteAction(
        tvShow: TvShowDetail
    ) {
        if (!tvShow.favorite) {
            viewModel.saveFavorite(FavoriteRequest(tvShow.id, FavoriteType.TVSHOW))
            btnFavorite.setImageDrawable(
                ResourceUtils.btnOn(this@DetailTvShowActivity)
            )
        } else {
            viewModel.deleteFavorite(FavoriteRequest(tvShow.id, FavoriteType.TVSHOW))
            btnFavorite.setImageDrawable(
                ResourceUtils.btnOff(this@DetailTvShowActivity)
            )
        }
        tvShow.favorite = !tvShow.favorite
    }

    private fun ContentDetailTvshowBinding.changeBtnStatus(
        tvShow: TvShowDetail
    ) {
        if (tvShow.favorite)
            btnFavorite.setImageDrawable(ResourceUtils.btnOn(this@DetailTvShowActivity))
        else
            btnFavorite.setImageDrawable(ResourceUtils.btnOff(this@DetailTvShowActivity))
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_title))
            .setText(resources.getString(R.string.share_text, detailTvShowBinding.tvTvshowTitle.text))
            .startChooser()
    }
}