package com.msaggik.cinema.presentation.view_model.state

import com.msaggik.cinema.domain.model.film.Film

sealed interface FilmsState {
    data object Loading : FilmsState

    data class Content(
        val films: List<Film>
    ) : FilmsState

    data class Error(
        val errorMessage: String
    ) : FilmsState

    data object  Empty : FilmsState
}