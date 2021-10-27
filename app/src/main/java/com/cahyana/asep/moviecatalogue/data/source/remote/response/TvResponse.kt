package com.cahyana.asep.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<TvResultsItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class TvResultsItem(

	@field:SerializedName("first_air_date")
	val firstAirDate: String?,

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("vote_average")
	val voteAverage: Double?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("poster_path")
	val posterPath: String?
)
