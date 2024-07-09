package com.msaggik.data.di

import androidx.room.Room
import com.msaggik.cinema.domain.repository.CharactersRepository
import com.msaggik.cinema.domain.repository.FilmsRepository
import com.msaggik.cinema.domain.repository.PlanetRepository
import com.msaggik.data.api.db.SwApiDataBase
import com.msaggik.data.api.network.NetworkClient
import com.msaggik.data.api.network.retrofit.RetrofitNetworkClient
import com.msaggik.data.repository_impl.CharactersRepositoryImpl
import com.msaggik.data.repository_impl.FilmsRepositoryImpl
import com.msaggik.data.repository_impl.PlanetRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val SW_API_BASE_URL = "https://swapi.dev"
private const val SW_DATA_BASE: String = "SwApiDb"

val dataModule = module {

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

    // network
    single<NetworkClient> {
        RetrofitNetworkClient(
            androidContext(),
            retrofit = get()
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(SW_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // db
    single {
        Room
            .databaseBuilder(
                androidContext(),
                SwApiDataBase::class.java,
                SW_DATA_BASE
            )
            .build()
    }
}