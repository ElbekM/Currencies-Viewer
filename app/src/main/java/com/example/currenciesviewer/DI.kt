package com.example.currenciesviewer

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initDI(context: Context) {
    startKoin {
        androidContext(context)
        modules(module {

        })
    }
}