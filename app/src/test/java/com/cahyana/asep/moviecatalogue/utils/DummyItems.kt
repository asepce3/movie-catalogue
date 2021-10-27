package com.cahyana.asep.moviecatalogue.utils

import com.cahyana.asep.moviecatalogue.data.*
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieWithGenre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowWithGenre
import com.cahyana.asep.moviecatalogue.data.source.remote.response.*

object DummyItems {

    fun getMovie(): Movie = Movie(
        "1",
        "Naruto Shippuden the Movie",
        "2007-08-04",
        5.4,
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg",
        false
    )

    fun getTvShow(): TvShow = TvShow(
        "1",
        "Naruto Shippuden the Movie",
        5.4,
        "2007-08-04",
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg",
        false
    )

    fun getFavorite(): Favorite = Favorite(
        "1",
        "Naruto Shippuden the Movie",
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg",
        6.8,
        FavoriteType.MOVIE.ordinal
    )

    fun getDummyMovie(): List<Movie> = arrayListOf(getMovie())

    fun getDummyTvShow(): List<TvShow> = arrayListOf(getTvShow())

    fun getDummyFavorite(): List<Favorite> = arrayListOf(getFavorite())

    fun getDummyTvShowWithGenre(): List<TvShowWithGenre> = arrayListOf(
        TvShowWithGenre(
            getTvShowDetail(), getGenres()
        )
    )

    private fun getGenres() = listOf(Genre("1", "Action"), Genre("2", "Animation"))

    fun getDummyMovieWithGenre(): List<MovieWithGenre> = arrayListOf(
        MovieWithGenre(getMovieDetail(), getGenres())
    )

    fun getDummyResultsItem(): List<MovieResultsItem> = arrayListOf(
        MovieResultsItem(
            "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
            "2007-08-04",
            8.4,
            3,
            "Naruto Shippuden the Movie",
            "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg"
        )
    )

    private fun getMovieDetail(): MovieDetail = MovieDetail(
        "1",
        "Naruto Shippuden the Movie",
        "2007-08-04",
        8.0,
        listOf(Genre("1", "Action"), Genre("2", "Animation")),
        34,
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg",
        false,
        true
    )

    private fun getTvShowDetail(): TvShowDetail = TvShowDetail(
        "1",
        "Naruto Shippuden the Movie",
        8.0,
        "2007-08-04",
        2,
        10,
        listOf(Genre("1", "Action"), Genre("2", "Animation")),
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg",
        false,
        true
    )



    fun getMovieResponse(): MovieResponse = MovieResponse(
        1,
        1,
        arrayListOf(MovieResultsItem(
            "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
            "2007-08-04",
            8.0,
            1,
            "Naruto Shippuden the Movie",
            "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg"
        )),
    1
    )

    fun getMovieResponses(): List<MovieResponse> = arrayListOf(getMovieResponse())

    fun getTvResponse(): TvResponse = TvResponse(
        1,
        1,
        arrayListOf(TvResultsItem(
            "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
            "2007-08-04",
            8.0,
            "Naruto Shippuden the Movie",
            1,
            "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg"
        )),
        1
    )

    fun getTvResponses(): List<TvResponse> = arrayListOf(getTvResponse())

    fun getMovieDetailResponse(): MovieDetailResponse = MovieDetailResponse(
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "2007-08-04",
        listOf(GenresItem(1, "Action"), GenresItem(2, "Animation")),
        8.2,
        40,
        3,
        "Naruto Shippuden the Movie",
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg"
    )

    fun getTvDetailResponse(): TvDetailResponse = TvDetailResponse(
        "Naruto (Japanese: NARUTO （ ナルト ） ) is a Japanese manga series written and illustrated by Masashi Kishimoto.",
        "2007-08-04",
        20,
        listOf(GenresItem(1, "Action"), GenresItem(2, "Animation")),
        8.2,
        "Naruto Shippuden the Movie",
        40,
        3,
        "/vDkct38sSFSWJIATlfJw0l3QOIR.jpg"
    )
}