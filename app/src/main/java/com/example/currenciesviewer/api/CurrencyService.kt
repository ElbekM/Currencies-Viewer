package com.example.currenciesviewer.api

import com.example.currenciesviewer.model.Currency
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject

class CurrencyService(private val apiController: ApiController) {

    val currenciesUpdated = PublishSubject.create<Currency>()

    fun getCurrencyList(): Completable = apiController.getCurrencyList()
        .map { it.toModel() }
        .doOnSuccess { currenciesUpdated.onNext(it) }
        .ignoreElement()
}
