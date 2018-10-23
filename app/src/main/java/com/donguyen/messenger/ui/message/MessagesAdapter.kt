package com.donguyen.messenger.ui.message

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.domain.model.Message
import com.donguyen.messenger.R

/**
 * Note: PagedList is content-immutable. This means that, although new content can be loaded into
 * an instance of PagedList, the loaded items themselves cannot change once loaded.
 * As such, if content in a PagedList updates, the PagedListAdapter object receives a
 * completely new PagedList that contains the updated information.
 *
 * Created by DoNguyen on 23/10/18.
 */
class MessagesAdapter(var listener: MessageViewHolder.OnDeleteAttachmentListener? = null)
    : PagedListAdapter<Message, MessageViewHolder>(COMPARATOR) {

    var selectionTracker: SelectionTracker<Long>? = null

    override fun getItemId(position: Int): Long {
        // getItem will not be null, because we don't support placeholder
        return getItem(position)!!.id
    }

    fun getMessage(position: Int): Message? {
        return super.getItem(position)
    }

    fun getAdapterPosition(messageId: Long): Int {
        currentList?.forEachIndexed { position, message ->
            if (message.id == messageId) return position
        }
        return RecyclerView.NO_POSITION
    }

    override fun getItemViewType(position: Int): Int {
        val userId = getItem(position)!!.userId
        return when (userId) {
            1L -> R.layout.item_my_message
            else -> R.layout.item_their_message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.create(parent, viewType, this, listener)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // message is only null when we enabled placeholder in the recycler view
        val message = getItem(position) ?: return
        val isSelected = selectionTracker?.isSelected(message.id) ?: false
        holder.bind(message, position, isSelected)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Message>() {

            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem == newItem
        }
    }
}