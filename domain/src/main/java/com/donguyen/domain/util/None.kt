package com.donguyen.domain.util

/**
 * Created by DoNguyen on 23/10/18.
 */
class None {

    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}