package io.github.msaggik.cinema.domain.model.film

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Film(
    val id: Int,
    val episodeId: Int,
    val title: String,
    val openingCrawl: String,
    val director: String,
    val producer: List<String>,
    val releaseDate: LocalDate?,
    val characters: List<Int>,
    val planets: List<Int>,
) : Parcelable
