package io.github.msaggik.cinema.domain.api

import io.github.msaggik.cinema.domain.model.character.Character

interface CharacterInteractor {
    fun getCharacter(filmId: Int, charactersId: List<Int>, consumer:  CharacterConsumer)

    interface CharacterConsumer {
        fun consume(listCharacters: List<Character>?, errorMessage: String?)
    }
}