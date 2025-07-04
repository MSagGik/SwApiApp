package io.github.msaggik.data.api.network.mappers_net_to_db

import androidx.core.net.toUri
import io.github.msaggik.data.api.db.dto.planet.PlanetDbDto
import io.github.msaggik.data.api.network.dto.response.entities.planet.PlanetNetDto

fun PlanetNetDto.toPlanetDb() =
    PlanetDbDto(
        planetId = url?.toUri()?.lastPathSegment?.toInt() ?: 0,
        name = name ?: "",
        rotationPeriod = rotationPeriod ?: "",
        orbitalPeriod = orbitalPeriod ?: "",
        diameter = diameter ?: "",
        climate = climate ?: "",
        gravity = gravity ?: "",
        terrain = terrain ?: "",
        population = population ?: ""
    )