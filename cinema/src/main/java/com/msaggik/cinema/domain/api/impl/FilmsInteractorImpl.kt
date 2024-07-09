package com.msaggik.cinema.domain.api.impl

import com.msaggik.cinema.domain.api.FilmsInteractor
import com.msaggik.cinema.domain.repository.FilmsRepository
import com.msaggik.common_util.Resource
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