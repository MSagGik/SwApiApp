package io.github.msaggik.data.api.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.msaggik.data.api.db.dto.character.CharacterDbDto

@Dao
interface CharactersDao {
//    @Query("SELECT * FROM characters WHERE character_id = :characterId")
//    fun getDbCharacters(characterId: Int): List<CharacterDbDto>

    @Query("SELECT * FROM characters WHERE filmsId LIKE '%*' ||:characterId || '*%'")
    fun getDbCharacters(characterId: Int): List<CharacterDbDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDbCharacters(characters: List<CharacterDbDto>): List<Long>
}