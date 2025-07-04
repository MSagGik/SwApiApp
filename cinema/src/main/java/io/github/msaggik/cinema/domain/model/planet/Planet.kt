package io.github.msaggik.cinema.domain.model.planet

data class Planet(
    val planetId: Int,
    val name: String,
    val rotationPeriod: String,
    val orbitalPeriod: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: List<String>,
    val population: List<String>
)
