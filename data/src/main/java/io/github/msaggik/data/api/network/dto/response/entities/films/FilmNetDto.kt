package io.github.msaggik.data.api.network.dto.response.entities.films

import com.google.gson.annotations.SerializedName

data class FilmNetDto(
    var title: String? = null,
    @SerializedName("episode_id")
    var episodeId: Int,
    @SerializedName("opening_crawl")
    var openingCrawl: String? = null,
    var director: String? = null,
    var producer: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    var characters: ArrayList<String> = arrayListOf(),
    var planets: ArrayList<String> = arrayListOf(),
    var starships: ArrayList<String> = arrayListOf(),
    var vehicles: ArrayList<String> = arrayListOf(),
    var species: ArrayList<String> = arrayListOf(),
    var created: String? = null,
    var edited: String? = null,
    @SerializedName("url")
    var id: String
)
