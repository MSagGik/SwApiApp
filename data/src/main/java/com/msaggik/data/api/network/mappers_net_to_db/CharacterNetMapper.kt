package com.msaggik.data.api.network.mappers_net_to_db

import androidx.core.net.toUri
import com.msaggik.data.api.db.dto.character.CharacterDbDto
import com.msaggik.data.api.network.dto.response.entities.character.CharacterNetDto

fun CharacterNetDto.toCharacterDb() =
    CharacterDbDto(
        characterId = url?.toUri()?.lastPathSegment?.toInt() ?: 0,
        name = name,
        height = height ?: "",
        birthYear = birthYear ?: "",
        homeWorld = homeworld?.toUri()?.lastPathSegment?.toInt() ?: 0
    )