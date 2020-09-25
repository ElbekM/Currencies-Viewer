package com.example.currenciesviewer.screens

import android.app.Application
import com.example.currenciesviewer.api.CurrencyService
import com.example.currenciesviewer.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(private val apiService: CurrencyService, application: Application) :
    BaseViewModel(application) {

    fun init() {

        apiService.currenciesUpdated
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currencies ->
                currencies
            }
            .addToSubscriptions()

        refreshData()
    }

    private fun refreshData() {
        apiService.getCurrencyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
            .addToSubscriptions()
    }
}