package com.donguyen.messenger.ui.message.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.messenger.ui.message.MessageViewHolder

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessageItemDetailsLookup(private val recyclerView: RecyclerView)
    : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is MessageViewHolder) {
                return viewHolder.getMessageItemDetails()
            }
        }

        return null
    }
}