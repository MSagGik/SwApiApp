package com.msaggik.data.api.db.mappers_db_to_cinema

import com.msaggik.cinema.domain.model.film.Film
import com.msaggik.common_util.Utils
import com.msaggik.data.api.db.dto.films.FilmDbDto

fun FilmDbDto.toFilm() =
    Film(
        id = id,
        episodeId = episodeId,
        title = title,
        openingCrawl = openingCrawl,
        director = director,
        producer = producer.split(","),
        releaseDate = Utils.getFormatDate(releaseDate),
        characters = characters,
        planets = planets
    )