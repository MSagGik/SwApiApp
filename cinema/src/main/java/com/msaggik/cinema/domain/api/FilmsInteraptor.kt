package com.msaggik.cinema.domain.api

import com.msaggik.cinema.domain.model.film.Film

interface FilmsInteraptor {
    fun getFilms(consumer:  FilmsConsumer)

    interface FilmsConsumer {
        fun consume(listFilms: List<Film>?, errorMessage: String?)
    }
}