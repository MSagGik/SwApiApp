package io.github.msaggik.data.di

import androidx.room.Room
import io.github.msaggik.data.api.db.SwApiDataBase
import io.github.msaggik.data.api.network.NetworkClient
import io.github.msaggik.data.api.network.retrofit.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val SW_API_BASE_URL = "https://swapi.dev"
private const val SW_DATA_BASE: String = "SwApiDb"

val dataModule = module {

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