package io.github.msaggik.data.api.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.msaggik.data.api.db.dto.planet.PlanetDbDto

@Dao
interface PlanetsDao {
    @Query("SELECT * FROM planets WHERE planet_id = :planetId")
    fun getDbPlanet(planetId: Int): PlanetDbDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDbPlanet(planet: PlanetDbDto)
}