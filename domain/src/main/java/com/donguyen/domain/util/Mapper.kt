package com.donguyen.domain.util

import com.donguyen.domain.usecase.Failure
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.Success

/**
 * Created by DoNguyen on 23/10/18.
 */
interface Mapper<From, To> {

    fun mapFrom(from: From): To

    fun mapFromList(fromList: List<From>): List<To> {
        return fromList.map { mapFrom(it) }
    }

    fun mapFromResult(fromResult: Result<From>): Result<To> {
        return when (fromResult) {
            is Success -> Result.success(mapFrom(fromResult.data))
            is Failure -> Result.failure(fromResult.error)
        }
    }

    fun mapFromResultList(fromResult: Result<List<From>>): Result<List<To>> {
        return when (fromResult) {
            is Success -> Result.success(mapFromList(fromResult.data))
            is Failure -> Result.failure(fromResult.error)
        }
    }
}