package com.example.currenciesviewer.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.currenciesviewer.base.livedata.LiveArgEvent
import com.example.currenciesviewer.base.livedata.LiveEvent
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val subscriptions: MutableList<Disposable> = mutableListOf()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = LiveEvent()
    val showMessageCommand = LiveArgEvent<String>()
    val showSnackBarCommand = LiveArgEvent<() -> Unit>()

    open fun destroy() {
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
    }

    open fun back() = closeCommand.call()

    open fun start() {}

    open fun stop() { }

    protected fun processError(
        tag: String = "ReminerApp",
        error: Throwable,
        display: Boolean = true
    ) {
        Log.e(tag, error.localizedMessage!!)

        if (display) showToast("Something went wrong")
    }

    protected fun showToast(message: String) = showMessageCommand.call(message)

    protected fun showSnackBar(action: () -> Unit) {
        showSnackBarCommand.call(action)
    }

    protected fun Disposable.addToSubscriptions() { subscriptions.add(this) }
}
