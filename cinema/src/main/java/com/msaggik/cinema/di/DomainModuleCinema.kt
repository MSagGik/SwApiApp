package com.msaggik.cinema.di

import com.msaggik.cinema.domain.api.CharacterInteractor
import com.msaggik.cinema.domain.api.FilmsInteractor
import com.msaggik.cinema.domain.api.PlanetInteractor
import com.msaggik.cinema.domain.api.impl.CharacterInteractorImpl
import com.msaggik.cinema.domain.api.impl.FilmsInteractorImpl
import com.msaggik.cinema.domain.api.impl.PlanetInteractorImpl
import org.koin.dsl.module

val domainModuleCinema = module {

    single<FilmsInteractor> {
        FilmsInteractorImpl(
            repository = get()
        )
    }

    single<CharacterInteractor> {
        CharacterInteractorImpl(
            repository = get()
        )
    }

    single<PlanetInteractor> {
        PlanetInteractorImpl(
            repository = get()
        )
    }
}