package com.donguyen.messenger.util

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers

/**
 * Created by DoNguyen on 29/10/18.
 */
fun onView(@IdRes id: Int): ViewInteraction {
    return Espresso.onView(ViewMatchers.withId(id))
}