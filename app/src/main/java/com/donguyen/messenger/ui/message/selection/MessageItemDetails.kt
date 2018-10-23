package com.donguyen.messenger.ui.message.selection

import androidx.recyclerview.selection.ItemDetailsLookup
import com.donguyen.domain.model.Message

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessageItemDetails(
        private val adapterPosition: Int,
        private val message: Message
) : ItemDetailsLookup.ItemDetails<Long>() {

    override fun getSelectionKey(): Long? {
        return message.id
    }

    override fun getPosition(): Int {
        return adapterPosition
    }
}