package io.github.msaggik.cinema.domain.api

import io.github.msaggik.cinema.domain.model.film.Film

interface FilmsInteractor {
    fun getFilms(consumer:  FilmsConsumer)

    interface FilmsConsumer {
        fun consume(listFilms: List<Film>?, errorMessage: String?)
    }
}