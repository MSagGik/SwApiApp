package io.github.msaggik.cinema.presentation.view_model.state

import io.github.msaggik.cinema.domain.model.character.Character

sealed interface CharactersState {
    data object Loading : CharactersState

    data class Content(
        val characters: List<Character>
    ) : CharactersState

    data class Error(
        val errorMessage: String
    ) : CharactersState

    data object  Empty : CharactersState
}