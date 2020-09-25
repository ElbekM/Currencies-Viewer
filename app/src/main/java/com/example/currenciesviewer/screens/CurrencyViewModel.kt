package com.example.currenciesviewer.screens

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.currenciesviewer.api.CurrencyService
import com.example.currenciesviewer.base.BaseViewModel
import com.example.currenciesviewer.base.livedata.SingleMutableLiveData
import com.example.currenciesviewer.model.Valute
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(private val apiService: CurrencyService, application: Application) :
    BaseViewModel(application) {

    val progressBarVisible = SingleMutableLiveData<Boolean>()
    val publicationDate = SingleMutableLiveData<String>()
    val valuteList = MutableLiveData<List<Valute>>()
    val isRefreshing = MutableLiveData<Boolean>()

    fun init() {
        progressBarVisible.value = true

        apiService.currenciesUpdated
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currencies ->
                valuteList.value = currencies.valute
                setActualDate(currencies.date)
            }
            .addToSubscriptions()

        refreshData()
    }

    fun refreshData() {
        apiService.getCurrencyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.value = !progressBarVisible.value!! }
            .doFinally {
                progressBarVisible.value = false
                isRefreshing.value = false
            }
            .subscribe({

            }, {

            })
            .addToSubscriptions()
    }

    private fun setActualDate(date: String) {
        publicationDate.value = date.subSequence(0, date.indexOfFirst{ it == 'T' }).toString()
    }
}
