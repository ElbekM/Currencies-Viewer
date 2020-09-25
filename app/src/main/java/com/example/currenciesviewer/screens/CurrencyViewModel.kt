package com.example.currenciesviewer.screens

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.currenciesviewer.api.CurrencyService
import com.example.currenciesviewer.base.BaseViewModel
import com.example.currenciesviewer.base.livedata.LiveArgEvent
import com.example.currenciesviewer.model.Valute
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(private val apiService: CurrencyService, application: Application) :
    BaseViewModel(application) {

    val publicationDate = LiveArgEvent<String>()
    val valuteList = MutableLiveData<List<Valute>>()

    fun init() {
        apiService.currenciesUpdated
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currencies ->
                valuteList.value = currencies.valute
                setDateAndTime(currencies.date)
            }
            .addToSubscriptions()

        refreshData()
    }

    private fun setDateAndTime(date: String) {
        publicationDate.call(date.subSequence(0, date.indexOfFirst{ it == 'T' }).toString())
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
