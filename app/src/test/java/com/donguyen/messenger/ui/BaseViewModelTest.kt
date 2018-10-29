package com.donguyen.messenger.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.donguyen.messenger.base.BaseViewModel
import com.donguyen.messenger.base.Event
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Base class for view model tests
 *
 * Created by DoNguyen on 29/10/18.
 */
@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTest {

    protected lateinit var eventObserver: Observer<Event<Any>>

    /**
     * To support running LiveData off-device
     * https://pbochenski.pl/blog/07-12-2017-testing_livedata.html
     */
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    /**
     * Call this after finished all set up from the children
     */
    @Before
    @Suppress("UNCHECKED_CAST")
    open fun setUp() {
        eventObserver = Mockito.mock(Observer::class.java) as Observer<Event<Any>>
        viewModel().events.observeForever(eventObserver)
    }

    /**
     * Return the view model under test
     */
    protected abstract fun viewModel(): BaseViewModel
}