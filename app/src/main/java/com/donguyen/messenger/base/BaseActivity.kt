package com.donguyen.messenger.base

import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by DoNguyen on 29/10/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    /*
     * *********************************************************************************************
     * TESTING
     * *********************************************************************************************
     */
    val idlingResource = CountingIdlingResource(this.toString())

    /**
     * Remember to call this method when extending from it
     */
    protected open fun handleEvent(event: Event<Any>) {
        when (event) {
            is IncreaseIdlingResource -> idlingResource.increment()
            is DecreaseIdlingResource -> idlingResource.decrement()
        }
    }
}