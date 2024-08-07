package com.msaggik.cinema.domain.api.impl

import com.msaggik.cinema.domain.api.PlanetInteractor
import com.msaggik.cinema.domain.repository.PlanetRepository
import com.msaggik.common_util.Resource
import java.util.concurrent.Executors

class PlanetInteractorImpl (
    private val repository: PlanetRepository
) : PlanetInteractor {

    override fun getPlanet(id: Int, consumer: PlanetInteractor.PlanetConsumer) {
        Executors.newCachedThreadPool().execute {
            when(val resource = repository.getPlanet(id)) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}