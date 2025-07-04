package io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema

import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.data.api.db.dto.character.CharacterDbDto

fun CharacterDbDto.toCharacter() =
    Character(
        characterId = characterId,
        name = name,
        height = height,
        birthYear = birthYear,
        filmsId = filmsId,
        homeWorld = homeWorld
    )