package com.msaggik.cinema.domain.repository

import com.msaggik.cinema.domain.model.planet.Planet
import com.msaggik.common_util.Resource

interface PlanetRepository {
    fun getPlanet(id: Int): Resource<Planet>
}