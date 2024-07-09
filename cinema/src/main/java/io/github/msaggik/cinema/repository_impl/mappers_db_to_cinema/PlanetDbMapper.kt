package io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema

import io.github.msaggik.cinema.domain.model.planet.Planet
import io.github.msaggik.data.api.db.dto.planet.PlanetDbDto

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