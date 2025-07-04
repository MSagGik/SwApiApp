package io.github.msaggik.cinema.di

import io.github.msaggik.cinema.domain.api.CharacterInteractor
import io.github.msaggik.cinema.repository_impl.CharactersRepositoryImpl
import io.github.msaggik.cinema.repository_impl.FilmsRepositoryImpl
import io.github.msaggik.cinema.repository_impl.PlanetRepositoryImpl
import io.github.msaggik.cinema.domain.api.FilmsInteractor
import io.github.msaggik.cinema.domain.api.PlanetInteractor
import io.github.msaggik.cinema.domain.api.impl.CharacterInteractorImpl
import io.github.msaggik.cinema.domain.api.impl.FilmsInteractorImpl
import io.github.msaggik.cinema.domain.api.impl.PlanetInteractorImpl
import io.github.msaggik.cinema.domain.repository.CharactersRepository
import io.github.msaggik.cinema.domain.repository.FilmsRepository
import io.github.msaggik.cinema.domain.repository.PlanetRepository
import org.koin.android.ext.koin.androidContext
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

    // repositories
    single<FilmsRepository> {
        FilmsRepositoryImpl(
            context = androidContext(),
            network = get(),
            dataBase = get()
        )
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(
            context = androidContext(),
            network = get(),
            dataBase = get()
        )
    }

    single<PlanetRepository> {
        PlanetRepositoryImpl(
            context = androidContext(),
            network = get(),
            dataBase = get()
        )
    }
}