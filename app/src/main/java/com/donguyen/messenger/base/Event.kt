package com.donguyen.messenger.base

/**
 * Created by DoNguyen on 29/10/18.
 *
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
sealed class Event<out T>(private val content: T?) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Mark the event as handled.
     */
    fun markHandled() {
        hasBeenHandled = true
    }

    /**
     * Returns the content and prevents its use again.
     */
    fun getContent(): T? {
        markHandled()
        return content
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T? = content
}

/**
 * Idling resource events
 */
sealed class IdlingResourceEvent<T>(content: T? = null) : Event<T>(content)

data class IncreaseIdlingResource<T>(val data: T? = null) : IdlingResourceEvent<T>(data)
data class DecreaseIdlingResource<T>(val data: T? = null) : IdlingResourceEvent<T>(data)

/**
 * Result events
 */
data class DeleteMessagesSuccess<T>(val data: T? = null) : Event<T>(data)

data class DeleteAttachmentSuccess<T>(val data: T? = null) : Event<T>(data)