package io.github.msaggik.data.api.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.msaggik.data.api.db.dto.films.FilmDbDto

@Dao
interface FilmsDao {
    @Query("SELECT * FROM films ORDER BY id ASC")
    fun getDbFilms(): List<FilmDbDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDbFilms(films: List<FilmDbDto>): List<Long>
}