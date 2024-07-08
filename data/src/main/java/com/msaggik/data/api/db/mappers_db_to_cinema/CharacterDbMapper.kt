package com.msaggik.data.api.db.mappers_db_to_cinema

import com.msaggik.cinema.domain.model.character.Character
import com.msaggik.data.api.db.dto.character.CharacterDbDto

fun CharacterDbDto.toCharacter() =
    Character(
        characterId = characterId,
        name = name,
        height = height,
        birthYear = birthYear,
        homeWorld = homeWorld
    )