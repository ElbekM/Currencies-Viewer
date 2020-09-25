package com.elbek.currenciesviewer

import android.content.Context
import com.elbek.currenciesviewer.api.ApiController
import com.elbek.currenciesviewer.api.ApiServiceProvider
import com.elbek.currenciesviewer.services.CurrencyService
import com.elbek.currenciesviewer.database.AppDatabase
import com.elbek.currenciesviewer.services.ValuteService
import com.elbek.currenciesviewer.screens.CurrencyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.single

fun initDI(context: Context) {
    startKoin {
        androidContext(context)
        modules(module {
            single<ApiServiceProvider>()
            single<CurrencyService>()
            single<ValuteService>()

            single { AppDatabase.newInstance(context) }
            single {
                this.get<ApiServiceProvider>(ApiServiceProvider::class, null, null)
                    .currencyRetrofit
                    .create(ApiController::class.java)
            }

            viewModel<CurrencyViewModel>()
        })
    }
}
