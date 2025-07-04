package io.github.msaggik.data.api.db.dto.films

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.msaggik.data.api.db.dto.films.converter.Converter

@Entity(
    tableName = "films"
)
@TypeConverters(Converter::class)
data class FilmDbDto(
    @PrimaryKey
    val id: Int,
    val episodeId: Int,
    val title: String,
    @ColumnInfo(name = "opening_crawl")
    val openingCrawl: String,
    val director: String,
    val producer: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val characters: List<Int>,
    val planets: List<Int>,
)
