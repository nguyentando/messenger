package com.donguyen.domain.usecase

import com.donguyen.domain.util.Transformer
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
abstract class UseCase<Input, Output>(private val transformer: Transformer<Result<Output>>? = null) {

    fun execute(input: Input): Observable<Result<Output>> {
        var result = buildObservable(input)

        if (transformer != null) {
            result = result.compose(transformer)
        }

        return result.onErrorReturn {
            // convert an error into a failure, so the caller don't need to handle onError
            Result.failure(it.message.orEmpty())
        }
    }

    protected abstract fun buildObservable(input: Input): Observable<Result<Output>>

}