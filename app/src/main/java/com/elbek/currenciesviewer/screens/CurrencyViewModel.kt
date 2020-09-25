package com.elbek.currenciesviewer.screens

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.elbek.currenciesviewer.services.CurrencyService
import com.elbek.currenciesviewer.base.BaseViewModel
import com.elbek.currenciesviewer.base.livedata.SingleMutableLiveData
import com.elbek.currenciesviewer.model.Valute
import com.elbek.currenciesviewer.services.ValuteService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyViewModel(
    private val apiService: CurrencyService,
    private val valuteService: ValuteService,
    application: Application
) : BaseViewModel(application) {

    private var valuteInfoDb: Pair<String, List<Valute>?>? = null

    val progressBarVisible = SingleMutableLiveData<Boolean>()
    val publicationDate = SingleMutableLiveData<String>()
    val valuteList = MutableLiveData<List<Valute>>()

    val isRefreshing = MutableLiveData<Boolean>()

    override fun destroy() {
        super.destroy()
        valuteService.disposeSubscriptions()
    }

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

        valuteService.valuteInfoUpdated
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { valuteInfoDb = it }
            .addToSubscriptions()

        valuteService.fillDatabase()
        getValutesFromDataBase()
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
            .subscribe({ }, {
                valuteInfoDb?.let {
                    valuteList.value = it.second
                    setActualDate(it.first)
                } ?: refreshData()

                showSnackBar { refreshData() }
                processError(error = it, display = false)
            })
            .addToSubscriptions()
    }

    private fun getValutesFromDataBase() {
        Single.just(valuteService)
            .flatMapCompletable { it.loadFromDatabase() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, { processError(error = it) })
            .addToSubscriptions()
    }

    private fun setActualDate(date: String?) {
        date?.let {
            publicationDate.value =
                it.subSequence(0, it.indexOfFirst { char -> char == 'T' }).toString()
        }
    }
}
