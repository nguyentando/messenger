package com.donguyen.messenger.util.rx

import androidx.lifecycle.Observer
import com.donguyen.domain.util.extension.exhaustive
import com.donguyen.messenger.base.Event

/**
 * Created by DoNguyen on 29/10/18.
 *
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event] has
 * already been handled.
 *
 * [onEventUnhandled] is *only* called if the [Event] has not been handled.
 */
class EventObserver<T>(private val onEventUnhandled: (Event<T>) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        when (event?.hasBeenHandled) {
            false -> {
                event.markHandled()
                onEventUnhandled(event)
            }
            else -> return
        }.exhaustive
    }
}