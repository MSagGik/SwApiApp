package com.msaggik.data.api.network.dto.response.entities.films

import com.msaggik.data.api.network.dto.response.Response

data class FilmsNetDto(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: ArrayList<FilmNetDto> = arrayListOf()
) : Response()
