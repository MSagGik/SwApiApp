package com.msaggik.cinema.domain.repository

import com.msaggik.cinema.domain.model.film.Film
import com.msaggik.common_util.Resource

interface FilmsRepository {
    fun getFilms(): Resource<List<Film>>
}