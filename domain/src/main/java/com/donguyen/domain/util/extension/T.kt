package com.donguyen.domain.util.extension

/**
 * Created by DoNguyen on 9/11/18.
 */

/**
 * Use this with [when] to turn [when] into an expression.
 * That way, we can force [when] to check all possible cases.
 */
val <T> T.exhaustive: T
    get() = this