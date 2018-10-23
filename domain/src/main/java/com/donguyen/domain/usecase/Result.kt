package com.donguyen.domain.usecase

/**
 * Created by DoNguyen on 23/10/18.
 */
sealed class Result<T> {
    companion object {
        fun <T> success(data: T): Success<T> {
            return Success(data = data)
        }

        fun <T> failure(error: String): Failure<T> {
            return Failure(error = error)
        }
    }
}

data class Success<T>(val data: T) : Result<T>()

data class Failure<T>(val error: String) : Result<T>()