package io.github.msaggik.cinema.domain.model.character

data class Character(
    val characterId: Int = 0,
    val name: String = "",
    val height: String = "",
    val birthYear: String = "",
    val filmsId: String = "",
    val homeWorld: Int = 0
)
