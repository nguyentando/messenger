package com.donguyen.messenger.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

/**
 * Created by DoNguyen on 23/10/18.
 */
open class BaseViewModel : ViewModel() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    /**
     * Observable stream of events. These events are only delivered once.
     */
    protected val mEvents = MutableLiveData<Event<Any>>()
    val events: LiveData<Event<Any>> = mEvents

    protected fun Disposable.autoClear() {
        subscriptions += this
    }

    private fun clearDisposables() {
        subscriptions.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    /**
     * To support testing
     */
    protected fun increaseIdlingResource() {
        mEvents.value = IncreaseIdlingResource()
    }

    protected fun decreaseIdlingResource() {
        mEvents.value = DecreaseIdlingResource()
    }
}