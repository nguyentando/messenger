package com.donguyen.messenger.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

/**
 * Created by DoNguyen on 23/10/18.
 */
open class BaseViewModel : ViewModel() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.autoClear() {
        subscriptions += this
    }

    private fun clearDisposables() {
        subscriptions.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }
}