package com.msaggik.cinema.presentation.view_model.state

import com.msaggik.cinema.domain.model.planet.Planet

sealed interface PlanetState {
    data object Loading : PlanetState

    data class Content(
        val planet: Planet
    ) : PlanetState

    data class Error(
        val errorMessage: String
    ) : PlanetState

    data object  Empty : PlanetState
}