package com.donguyen.messenger.util.rx

import androidx.lifecycle.Observer
import com.donguyen.messenger.base.Event

/**
 * Created by DoNguyen on 29/10/18.
 *
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (Event<T>) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        when (event?.hasBeenHandled) {
            true -> return
            false -> {
                event.markHandled()
                onEventUnhandledContent(event)
            }
        }
    }
}