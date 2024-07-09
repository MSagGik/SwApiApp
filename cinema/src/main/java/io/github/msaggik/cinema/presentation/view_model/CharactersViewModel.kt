package io.github.msaggik.cinema.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.msaggik.cinema.domain.api.CharacterInteractor
import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.cinema.presentation.view_model.state.CharactersState

class CharactersViewModel(
    private val filmId: Int,
    private val charactersId: List<Int>,
    private val characterIteractor: CharacterInteractor
) : ViewModel() {

    init {
        readCharacters()
    }

    private val stateLiveData = MutableLiveData<CharactersState>()
    fun getStateLiveData(): LiveData<CharactersState> = mediatorStateLiveData

    private val mediatorStateLiveData = MediatorLiveData<CharactersState>().also { liveData ->
        liveData.addSource(stateLiveData) { state ->
            liveData.value = when (state) {
                is CharactersState.Content -> CharactersState.Content(state.characters)
                is CharactersState.Empty -> state
                is CharactersState.Error -> state
                is CharactersState.Loading -> state
            }
        }
    }

    private fun readCharacters() {
        characterIteractor.getCharacter(
            filmId = filmId,
            charactersId = charactersId,
            object : CharacterInteractor.CharacterConsumer {
                override fun consume(listCharacters: List<Character>?, errorMessage: String?) {
                    val characters = mutableListOf<Character>()
                    if (listCharacters != null) {
                        characters.addAll(listCharacters)
                    }
                    when {
                        errorMessage != null -> renderState(CharactersState.Error(errorMessage = errorMessage))
                        characters.isEmpty() -> renderState(CharactersState.Empty)
                        else -> renderState(CharactersState.Content(characters = characters))
                    }
                }
            })
    }

    private fun renderState(state: CharactersState) {
        stateLiveData.postValue(state)
    }
}