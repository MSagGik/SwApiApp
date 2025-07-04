package io.github.msaggik.data.api.network.dto.response.entities.films

import io.github.msaggik.data.api.network.dto.response.ResponseNetwork

data class FilmsNetDto(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: ArrayList<FilmNetDto> = arrayListOf()
) : ResponseNetwork()
