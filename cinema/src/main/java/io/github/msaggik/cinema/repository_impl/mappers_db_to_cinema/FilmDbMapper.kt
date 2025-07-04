package io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema

import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.util.Utils
import io.github.msaggik.data.api.db.dto.films.FilmDbDto

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