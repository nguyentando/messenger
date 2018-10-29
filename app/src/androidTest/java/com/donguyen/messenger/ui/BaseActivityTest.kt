package com.donguyen.messenger.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.donguyen.messenger.base.BaseActivity
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Base class for activity tests.
 *
 * Created by DoNguyen on 29/10/18.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
abstract class BaseActivityTest {

    abstract fun activityTestRule(): ActivityTestRule<out BaseActivity>

    @Before
    open fun setUp() {
        IdlingRegistry.getInstance().register(activityTestRule().activity.idlingResource)
    }

    @After
    open fun tearDown() {
        IdlingRegistry.getInstance().unregister(activityTestRule().activity.idlingResource)
    }
}