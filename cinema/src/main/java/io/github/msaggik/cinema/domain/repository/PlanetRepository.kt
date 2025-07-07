package io.github.msaggik.cinema.domain.repository

import io.github.msaggik.cinema.domain.model.planet.Planet
import io.github.msaggik.util.Resource

interface PlanetRepository {
    fun getPlanet(id: Int): Resource<Planet>
}