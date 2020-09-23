package com.example.currenciesviewer

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI(applicationContext)
    }
}
