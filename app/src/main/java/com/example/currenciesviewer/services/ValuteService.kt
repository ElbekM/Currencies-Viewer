package com.example.currenciesviewer.services

import com.example.currenciesviewer.database.AppDatabase
import com.example.currenciesviewer.model.entities.ValuteEntity
import com.example.currenciesviewer.model.entities.ValuteInfoEntity
import com.example.currenciesviewer.model.entities.toModel
import com.example.currenciesviewer.model.Currency
import com.example.currenciesviewer.model.Valute
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ValuteService(
    private val database: AppDatabase,
    private val currencyService: CurrencyService
) {

    private var valuteServiceSubscription: Disposable? = null

    val valuteInfoUpdated = PublishSubject.create<Pair<String, List<Valute>>>()

    fun fillDatabase() {
        currencyService.currenciesUpdated
            .subscribe { addToDatabase(it) }
            .let { valuteServiceSubscription = it }
    }

    fun loadFromDatabase(): Completable =
        Single.just(database.getDao())
            .observeOn(Schedulers.io())
            .flatMap { it.getAll() }
            .map { Pair(it.date, it.valute.toModel()) }
            .doOnSuccess { valuteInfoUpdated.onNext(it) }
            .ignoreElement()

    fun disposeSubscriptions() {
        valuteServiceSubscription?.dispose()
    }

    private fun addToDatabase(valuteInfo: Currency) =
        Single.fromCallable { ValuteEntity.createList(valuteInfo.valute) }
            .observeOn(Schedulers.io())
            .flatMapCompletable {
                database.getDao().updateValuteInfo(
                    ValuteInfoEntity(
                        date = valuteInfo.date,
                        valute = it.toMutableList()
                    )
                )
            }
}
