package com.msaggik.cinema.domain.model.film

import java.time.LocalDate

data class Film(
    val id: Int,
    val title: String,
    val openingCrawl: String,
    val director: String,
    val producer: List<String>,
    val releaseDate: LocalDate?,
    val characters: List<Int>,
    val planets: List<Int>,
)
