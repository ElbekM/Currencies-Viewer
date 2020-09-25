package com.elbek.currenciesviewer.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.elbek.currenciesviewer.base.livedata.LiveArgEvent
import com.elbek.currenciesviewer.base.livedata.LiveEvent
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val subscriptions: MutableList<Disposable> = mutableListOf()

    val closeCommand = LiveEvent()
    val showSnackBarCommand = LiveArgEvent<() -> Unit>()

    open fun destroy() {
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
    }

    open fun back() = closeCommand.call()

    protected fun processError(error: Throwable) {
        Log.e("CurrenciesApp", error.localizedMessage!!)
    }

    protected fun showSnackBar(action: () -> Unit) {
        showSnackBarCommand.call(action)
    }

    protected fun Disposable.addToSubscriptions() { subscriptions.add(this) }
}
