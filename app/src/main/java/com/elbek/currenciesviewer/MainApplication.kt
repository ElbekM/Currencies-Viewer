package com.elbek.currenciesviewer

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI(applicationContext)
    }
}
