package com.donguyen.messenger.ui.message

import androidx.paging.PagedList
import com.donguyen.domain.model.Message

/**
 * Created by DoNguyen on 23/10/18.
 */
data class MessagesViewState(
        var loading: Boolean = false,
        var error: String = "",
        var messages: PagedList<Message>? = null
)