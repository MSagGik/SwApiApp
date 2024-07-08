package com.msaggik.data.api.network.mappers_net_to_db

import com.msaggik.data.api.db.dto.films.FilmDbDto
import com.msaggik.data.api.network.dto.response.entities.films.FilmNetDto

fun FilmNetDto.toFilmDb() =
    FilmDbDto(
        id = episodeId,
        title = title ?: "",
        openingCrawl = openingCrawl ?: "",
        director = director ?: "",
        producer = producer ?: "",
        releaseDate = releaseDate ?: "",
        characters = characters,
        planets = planets
    )