package com.msaggik.cinema.domain.api

import com.msaggik.cinema.domain.model.character.Character

interface CharacterInteraptor {
    fun getCharacter(filmId: Int, charactersId: List<Int>, consumer:  CharacterConsumer)

    interface CharacterConsumer {
        fun consume(listCharacters: List<Character>?, errorMessage: String?)
    }
}