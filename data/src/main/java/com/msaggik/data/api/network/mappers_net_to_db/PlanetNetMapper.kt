package com.msaggik.data.api.network.mappers_net_to_db

import androidx.core.net.toUri
import com.msaggik.data.api.db.dto.planet.PlanetDbDto
import com.msaggik.data.api.network.dto.response.entities.planet.PlanetNetDto

fun PlanetNetDto.toPlanetDb() =
    PlanetDbDto(
        planetId = url?.toUri()?.lastPathSegment?.toInt() ?: 0,
        name = name ?: "",
        rotationPeriod = rotationPeriod ?: "",
        orbitalPeriod = orbitalPeriod ?: "",
        diameter = diameter ?: 0,
        climate = climate ?: "",
        gravity = gravity ?: "",
        terrain = terrain ?: "",
        population = population ?: ""
    )