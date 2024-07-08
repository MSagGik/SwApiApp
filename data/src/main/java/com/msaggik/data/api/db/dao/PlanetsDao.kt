package com.msaggik.data.api.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msaggik.data.api.db.dto.planet.PlanetDbDto

interface PlanetsDao {
    @Query("SELECT * FROM planets WHERE planet_id = :planetId")
    fun getDbPlanet(planetId: Int): PlanetDbDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDbPlanet(planet: PlanetDbDto)
}