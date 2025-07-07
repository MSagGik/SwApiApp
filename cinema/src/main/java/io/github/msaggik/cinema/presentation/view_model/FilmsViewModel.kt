package io.github.msaggik.cinema.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.msaggik.cinema.domain.api.FilmsInteractor
import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.cinema.presentation.view_model.state.FilmsState

class FilmsViewModel(
    private val filmsInteractor: FilmsInteractor
) : ViewModel() {

    init {
        readFilms()
    }

    private val stateLiveData = MutableLiveData<FilmsState>()
    fun getStateLiveData(): LiveData<FilmsState> = mediatorStateLiveData

    private val mediatorStateLiveData = MediatorLiveData<FilmsState>().also { liveData ->
        liveData.addSource(stateLiveData) { state ->
            liveData.value = when (state) {
                is FilmsState.Content -> FilmsState.Content(state.films)
                is FilmsState.Empty -> state
                is FilmsState.Error -> state
                is FilmsState.Loading -> state
            }
        }
    }

    private fun readFilms() {
        filmsInteractor.getFilms(object : FilmsInteractor.FilmsConsumer {
            override fun consume(listFilms: List<Film>?, errorMessage: String?) {
                val films = mutableListOf<Film>()
                if (listFilms != null) {
                    films.addAll(listFilms)
                }
                when {
                    errorMessage != null -> renderState(FilmsState.Error(errorMessage = errorMessage))
                    films.isEmpty() -> renderState(FilmsState.Empty)
                    else -> renderState(FilmsState.Content(films = films))
                }
            }
        })
    }

    private fun renderState(state: FilmsState) {
        stateLiveData.postValue(state)
    }
}