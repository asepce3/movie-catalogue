package com.cahyana.asep.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvDetailResponse(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("number_of_episodes")
	val numberOfEpisodes: Int,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("number_of_seasons")
	val numberOfSeasons: Int,

	@field:SerializedName("poster_path")
	val posterPath: String
)

data class GenresItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String
)