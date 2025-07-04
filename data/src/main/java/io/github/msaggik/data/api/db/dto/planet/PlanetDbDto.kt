package io.github.msaggik.data.api.db.dto.planet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "planets"
)
data class PlanetDbDto(
    @PrimaryKey
    @ColumnInfo(name = "planet_id")
    val planetId: Int,
    val name: String,
    @ColumnInfo(name = "rotation_period")
    val rotationPeriod: String,
    @ColumnInfo(name = "orbital_period")
    val orbitalPeriod: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val population: String
)
