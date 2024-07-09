package com.msaggik.cinema.di

import com.msaggik.cinema.presentation.view_model.CharactersViewModel
import com.msaggik.cinema.presentation.view_model.FilmsViewModel
import com.msaggik.cinema.presentation.view_model.PlanetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleCinema = module {

    viewModel {
        FilmsViewModel(
            filmsInteractor = get()
        )
    }

    viewModel { ( filmId: Int, charactersId: List<Int>) ->
        CharactersViewModel(
            filmId = filmId,
            charactersId = charactersId,
            characterIteractor = get()
        )
    }

    viewModel { (id: Int) ->
        PlanetViewModel(
            id = id,
            planetInteractor = get()
        )
    }
}