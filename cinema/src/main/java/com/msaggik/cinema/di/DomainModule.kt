package com.msaggik.cinema.di

import com.msaggik.cinema.domain.api.CharacterInteraptor
import com.msaggik.cinema.domain.api.FilmsInteraptor
import com.msaggik.cinema.domain.api.PlanetInteraptor
import com.msaggik.cinema.domain.api.impl.CharacterInteraptorImpl
import com.msaggik.cinema.domain.api.impl.FilmsInteraptorImpl
import com.msaggik.cinema.domain.api.impl.PlanetInteraptorImpl
import org.koin.dsl.module

val domainModule = module {

    single<FilmsInteraptor> {
        FilmsInteraptorImpl(
            repository = get()
        )
    }

    single<CharacterInteraptor> {
        CharacterInteraptorImpl(
            repository = get()
        )
    }

    single<PlanetInteraptor> {
        PlanetInteraptorImpl(
            repository = get()
        )
    }
}