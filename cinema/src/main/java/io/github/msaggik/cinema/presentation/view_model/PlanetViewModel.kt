package io.github.msaggik.cinema.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.msaggik.cinema.domain.api.PlanetInteractor
import io.github.msaggik.cinema.domain.model.planet.Planet
import io.github.msaggik.cinema.presentation.view_model.state.PlanetState

class PlanetViewModel(
    private val id: Int,
    private val planetInteractor: PlanetInteractor
) : ViewModel() {

    init {
        readPlanet()
    }

    private val stateLiveData = MutableLiveData<PlanetState>()
    fun getStateLiveData(): LiveData<PlanetState> = mediatorStateLiveData

    private val mediatorStateLiveData = MediatorLiveData<PlanetState>().also { liveData ->
        liveData.addSource(stateLiveData) { state ->
            liveData.value = when (state) {
                is PlanetState.Content -> PlanetState.Content(state.planet)
                is PlanetState.Empty -> state
                is PlanetState.Error -> state
                is PlanetState.Loading -> state
            }
        }
    }

    private fun readPlanet() {
        planetInteractor.getPlanet(
            id = id,
            object : PlanetInteractor.PlanetConsumer {
                override fun consume(planet: Planet?, errorMessage: String?) {
                    when {
                        errorMessage != null -> renderState(PlanetState.Error(errorMessage = errorMessage))
                        planet == null -> renderState(PlanetState.Empty)
                        else -> renderState(PlanetState.Content(planet = planet))
                    }
                }
            })
    }

    private fun renderState(state: PlanetState) {
        stateLiveData.postValue(state)
    }
}