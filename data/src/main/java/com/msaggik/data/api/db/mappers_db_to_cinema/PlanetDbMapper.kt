package com.msaggik.data.api.db.mappers_db_to_cinema

import com.msaggik.cinema.domain.model.planet.Planet
import com.msaggik.data.api.db.dto.planet.PlanetDbDto

fun PlanetDbDto.toPlanet() =
    Planet(
        planetId = planetId,
        name = name,
        rotationPeriod = rotationPeriod,
        orbitalPeriod = orbitalPeriod,
        diameter = diameter,
        climate = climate,
        gravity = gravity,
        terrain = terrain.split(","),
        population = population.split(",")
    )