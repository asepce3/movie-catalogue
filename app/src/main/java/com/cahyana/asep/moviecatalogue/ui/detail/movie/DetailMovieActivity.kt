package com.cahyana.asep.moviecatalogue.ui.detail.movie

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
import com.cahyana.asep.moviecatalogue.data.MovieDetail
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.cahyana.asep.moviecatalogue.databinding.ContentDetailMovieBinding
import com.cahyana.asep.moviecatalogue.utils.ResourceUtils
import com.cahyana.asep.moviecatalogue.viewmodel.ViewModelFactory
import com.cahyana.asep.moviecatalogue.vo.Status

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var detailMovieBinding: ContentDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailMovieBinding = binding.detailMovie
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE)
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId)
                viewModel.getMovie().observe(this, { movie ->
                    when(movie.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            if (movie.data != null) {
                                populateMovie(toMovieDetail(movie.data))
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, movie.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun toMovieDetail(movie: MovieWithGenre): MovieDetail {
        return MovieDetail(
            movie.movie.id,
            movie.movie.title,
            movie.movie.release,
            movie.movie.score,
            movie.genres,
            movie.movie.duration,
            movie.movie.overview,
            movie.movie.imagePath,
            movie.movie.favorite,
            movie.movie.detail
        )
    }

    private fun populateMovie(movie: MovieDetail) {
        with(detailMovieBinding) {
            tvMovieTitle.text = movie.title
            tvMovieDesc.text = movie.overview
            tvMovieDuration.text = movie.duration.toString()
            val genreTxt = arrayListOf<String>()
            movie.genre.forEach { genreTxt.add(it.name) }
            tvMovieGenre.text = genreTxt.joinToString()
            tvMovieRelease.text = movie.release
            ratingBar.rating = movie.score.toFloat() / 2f

            Glide.with(imagePoster.context)
                .load(String.format(Config.IMAGE_PATH, movie.imagePath))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_replay))
                .error(R.drawable.ic_panorama)
                .into(imagePoster)

            changeBtnStatus(movie)

            btnFavorite.setOnClickListener {
                favoriteAction(movie)
            }

            btnShare.setOnClickListener {
                share()
            }
        }
    }

    private fun ContentDetailMovieBinding.favoriteAction(
        movie: MovieDetail
    ) {
        if (!movie.favorite) {
            viewModel.saveFavorite(FavoriteRequest(movie.id, FavoriteType.MOVIE))
            btnFavorite.setImageDrawable(
                ResourceUtils.btnOn(this@DetailMovieActivity)
            )
        } else {
            viewModel.deleteFavorite(FavoriteRequest(movie.id, FavoriteType.MOVIE))
            btnFavorite.setImageDrawable(
                ResourceUtils.btnOff(this@DetailMovieActivity)
            )
        }
        movie.favorite = !movie.favorite
    }

    private fun ContentDetailMovieBinding.changeBtnStatus(
        movie: MovieDetail
    ) {
        if (movie.favorite)
            btnFavorite.setImageDrawable(ResourceUtils.btnOn(this@DetailMovieActivity))
        else
            btnFavorite.setImageDrawable(ResourceUtils.btnOff(this@DetailMovieActivity))
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_title))
            .setText(resources.getString(R.string.share_text, detailMovieBinding.tvMovieTitle.text))
            .startChooser()
    }
}
