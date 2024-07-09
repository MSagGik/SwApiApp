package io.github.msaggik.cinema.repository_impl

import android.content.Context
import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.cinema.domain.repository.CharactersRepository
import io.github.msaggik.ui.R
import io.github.msaggik.util.Resource
import io.github.msaggik.data.api.db.SwApiDataBase
import io.github.msaggik.data.api.db.dto.character.CharacterDbDto
import io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema.toCharacter
import io.github.msaggik.data.api.network.NetworkClient
import io.github.msaggik.data.api.network.dto.response.entities.character.CharacterNetDto
import io.github.msaggik.data.api.network.mappers_net_to_db.toCharacterDb

class CharactersRepositoryImpl(
    private val context: Context,
    private val network: NetworkClient,
    private val dataBase: SwApiDataBase
) : CharactersRepository {

    private val charactersDao = dataBase.charactersDao()

    override fun getCharacters(
        filmId: Int,
        charactersId: List<Int>
    ): Resource<List<Character>> {

        val listCharacter: MutableList<CharacterDbDto> = emptyList<CharacterDbDto>().toMutableList()
        var characterDb = charactersDao.getDbCharacters(filmId)

        if (characterDb.size == charactersId.size) {
            return Resource.Success(characterDb.map { it.toCharacter() })
        } else {
            for(id in charactersId) {
                val response = network.doRequestGetCharacter(id)
                when (response.resultNetworkCode) {
                    -1 -> return Resource.Error(context.getString(R.string.no_connection))
                    -2 -> return Resource.Error(context.getString(R.string.something_went_wrong))
                    200 -> listCharacter.add((response as CharacterNetDto).toCharacterDb())
                    else -> return Resource.Error(context.getString(R.string.error))
                }
            }
            charactersDao.insertDbCharacters(listCharacter)
            return Resource.Success(listCharacter.map { it.toCharacter() })
        }
    }
}