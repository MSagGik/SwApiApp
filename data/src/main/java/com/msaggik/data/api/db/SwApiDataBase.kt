package com.msaggik.data.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msaggik.data.api.db.dao.CharactersDao
import com.msaggik.data.api.db.dao.FilmsDao
import com.msaggik.data.api.db.dao.PlanetsDao
import com.msaggik.data.api.db.dto.character.CharacterDbDto
import com.msaggik.data.api.db.dto.films.FilmDbDto
import com.msaggik.data.api.db.dto.films.converter.Converter
import com.msaggik.data.api.db.dto.planet.PlanetDbDto

private const val VERSION_DATA_BASE = 1
@Database(
    entities = [
        FilmDbDto::class,
        CharacterDbDto::class,
        PlanetDbDto::class
    ], version = VERSION_DATA_BASE
)
@TypeConverters(Converter::class)
abstract class SwApiDataBase() : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
    abstract fun charactersDao(): CharactersDao
    abstract fun planetsDao(): PlanetsDao
}
