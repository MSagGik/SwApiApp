package io.github.msaggik.swapiapp.root

import android.app.Application
import io.github.msaggik.cinema.di.domainModuleCinema
import io.github.msaggik.cinema.di.viewModelModuleCinema
import io.github.msaggik.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModuleCinema, viewModelModuleCinema)
        }
    }
}