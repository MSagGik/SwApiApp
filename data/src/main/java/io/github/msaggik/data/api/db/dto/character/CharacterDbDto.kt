package io.github.msaggik.data.api.db.dto.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.msaggik.data.api.db.dto.films.converter.Converter

@Entity(
    tableName = "characters"
)
data class CharacterDbDto(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: Int,
    val name: String = "",
    val height: String = "",
    @ColumnInfo(name = "birth_year")
    val birthYear: String = "",
    val filmsId: String = "",
    @ColumnInfo(name = "home_world")
    val homeWorld: Int = 0
)
