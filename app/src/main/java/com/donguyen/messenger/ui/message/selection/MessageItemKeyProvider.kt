package com.donguyen.messenger.ui.message.selection

import androidx.recyclerview.selection.ItemKeyProvider
import com.donguyen.messenger.ui.message.MessagesAdapter

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessageItemKeyProvider(private val adapter: MessagesAdapter) : ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(adapterPosition: Int): Long? {
        return adapter.getItemId(adapterPosition)
    }

    override fun getPosition(key: Long): Int {
        return adapter.getAdapterPosition(key)
    }
}