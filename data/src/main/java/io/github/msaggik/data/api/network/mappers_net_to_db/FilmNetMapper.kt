package io.github.msaggik.data.api.network.mappers_net_to_db

import androidx.core.net.toUri
import io.github.msaggik.data.api.db.dto.films.FilmDbDto
import io.github.msaggik.data.api.network.dto.response.entities.films.FilmNetDto

fun FilmNetDto.toFilmDb() =
    FilmDbDto(
        id = id.toUri().lastPathSegment?.toInt() ?: 0,
        episodeId = episodeId,
        title = title ?: "",
        openingCrawl = openingCrawl ?: "",
        director = director ?: "",
        producer = producer ?: "",
        releaseDate = releaseDate ?: "",
        characters = characters.map { it.toUri().lastPathSegment?.toInt() ?: 0 },
        planets = planets.map { it.toUri().lastPathSegment?.toInt() ?: 0 }
    )