package io.github.msaggik.cinema.domain.api.impl

import io.github.msaggik.cinema.domain.api.FilmsInteractor
import io.github.msaggik.cinema.domain.repository.FilmsRepository
import io.github.msaggik.util.Resource
import java.util.concurrent.Executors

class FilmsInteractorImpl (
    private val repository: FilmsRepository
) : FilmsInteractor {

    override fun getFilms(consumer: FilmsInteractor.FilmsConsumer) {
        Executors.newCachedThreadPool().execute {
            when(val resource = repository.getFilms()) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}