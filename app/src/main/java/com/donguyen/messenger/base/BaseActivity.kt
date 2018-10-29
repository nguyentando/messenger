package com.donguyen.messenger.base

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by DoNguyen on 29/10/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * Remember to call this method when extending from it
     */
    protected open fun handleEvent(event: Event<Any>) {

    }
}