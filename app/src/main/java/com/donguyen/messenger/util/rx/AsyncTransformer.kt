package com.donguyen.messenger.util.rx

import com.donguyen.domain.util.Transformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by DoNguyen on 23/10/18.
 */
class AsyncTransformer<T> : Transformer<T>() {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}