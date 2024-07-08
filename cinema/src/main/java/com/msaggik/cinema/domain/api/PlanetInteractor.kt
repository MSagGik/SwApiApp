package com.msaggik.cinema.domain.api

import com.msaggik.cinema.domain.model.planet.Planet

interface PlanetInteractor {
    fun getPlanet(id: Int, consumer:  PlanetConsumer)

    interface PlanetConsumer {
        fun consume(planet: Planet?, errorMessage: String?)
    }
}