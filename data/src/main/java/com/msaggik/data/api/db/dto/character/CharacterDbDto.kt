package com.msaggik.data.api.db.dto.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters"
)
data class CharacterDbDto(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: Int = 0,
    val name: String = "",
    val height: String = "",
    @ColumnInfo(name = "birth_year")
    val birthYear: String = "",
    @ColumnInfo(name = "home_world")
    val homeWorld: Int = 0
)
