package com.msaggik.cinema.domain.repository

import com.msaggik.cinema.domain.model.character.Character
import com.msaggik.common_util.Resource

interface CharactersRepository {
    fun getCharacters(
        filmId: Int,
        charactersId: List<Int>
    ): Resource<List<Character>>
}