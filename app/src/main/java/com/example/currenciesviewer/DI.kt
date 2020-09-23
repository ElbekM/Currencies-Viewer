package com.example.currenciesviewer

import android.content.Context
import com.example.currenciesviewer.screens.CurrencyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initDI(context: Context) {
    startKoin {
        androidContext(context)
        modules(module {
            viewModel<CurrencyViewModel>()
        })
    }
}
