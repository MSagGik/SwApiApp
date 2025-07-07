package io.github.msaggik.cinema.domain.repository

import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.util.Resource

interface CharactersRepository {
    fun getCharacters(
        filmId: Int,
        charactersId: List<Int>
    ): Resource<List<Character>>
}