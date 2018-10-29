package com.donguyen.messenger.util.extension

import android.app.Activity
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers

/**
 * Created by DoNguyen on 29/10/18.
 */
fun ActivityTestRule<out Activity>.string(@StringRes resId: Int): String = activity.getString(resId)

fun ActivityTestRule<out Activity>.onToast(message: String): ViewInteraction {
    return Espresso.onView(ViewMatchers.withText(message))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activity.window.decorView)))
}

fun ActivityTestRule<out Activity>.onToast(@StringRes messageResId: Int): ViewInteraction {
    return Espresso.onView(ViewMatchers.withText(messageResId))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activity.window.decorView)))
}