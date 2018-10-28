package com.donguyen.domain.util

import io.reactivex.Observable
import io.reactivex.ObservableSource

/**
 * Created by DoNguyen on 28/10/18.
 */
class TestTransformer<T> : Transformer<T>() {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
    }
}