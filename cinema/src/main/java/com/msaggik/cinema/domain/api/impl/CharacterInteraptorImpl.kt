package com.msaggik.cinema.domain.api.impl

import com.msaggik.cinema.domain.api.CharacterInteraptor
import com.msaggik.cinema.domain.repository.CharactersRepository
import com.msaggik.common_util.Resource
import java.util.concurrent.Executors

class CharacterInteraptorImpl (
    private val repository: CharactersRepository
) : CharacterInteraptor {

    override fun getCharacter(
        filmId: Int,
        charactersId: List<Int>,
        consumer: CharacterInteraptor.CharacterConsumer
    ) {
        Executors.newCachedThreadPool().execute {
            when(val resource = repository.getCharacters(filmId, charactersId)) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}