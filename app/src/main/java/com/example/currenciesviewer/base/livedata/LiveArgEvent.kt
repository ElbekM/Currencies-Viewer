package com.example.currenciesviewer.base.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class LiveArgEvent<T> : (T) -> Unit() {

    private val liveData =
        SingleMutableLiveData<T>()

    fun observe(owner: LifecycleOwner, observer: Observer<in T>) = liveData.observe(owner, observer)

    override fun invoke(arg: T) = call(arg)

    @MainThread
    fun call(value: T) {
        liveData.value = value
    }
}
