package com.example.currenciesviewer

import android.content.Context
import com.example.currenciesviewer.api.ApiController
import com.example.currenciesviewer.api.ApiServiceProvider
import com.example.currenciesviewer.api.CurrencyService
import com.example.currenciesviewer.screens.CurrencyViewModel
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

            single {
                this.get<ApiServiceProvider>(ApiServiceProvider::class, null, null)
                    .currencyRetrofit
                    .create(ApiController::class.java)
            }

            viewModel<CurrencyViewModel>()
        })
    }
}
