package io.github.msaggik.cinema.domain.api.impl

import io.github.msaggik.cinema.domain.api.CharacterInteractor
import io.github.msaggik.cinema.domain.repository.CharactersRepository
import io.github.msaggik.util.Resource
import java.util.concurrent.Executors

class CharacterInteractorImpl (
    private val repository: CharactersRepository
) : CharacterInteractor {

    override fun getCharacter(
        filmId: Int,
        charactersId: List<Int>,
        consumer: CharacterInteractor.CharacterConsumer
    ) {
        Executors.newCachedThreadPool().execute {
            when(val resource = repository.getCharacters(filmId, charactersId)) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}