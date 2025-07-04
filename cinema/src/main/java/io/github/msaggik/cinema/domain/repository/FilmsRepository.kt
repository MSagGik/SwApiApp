package io.github.msaggik.cinema.domain.repository

import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.util.Resource

interface FilmsRepository {
    fun getFilms(): Resource<List<Film>>
}