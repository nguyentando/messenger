package com.donguyen.messenger.ui.message

import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.forEach
import androidx.paging.PagedListAdapter
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.domain.model.Message
import com.donguyen.messenger.R
import com.donguyen.messenger.ui.customview.AttachmentView
import com.donguyen.messenger.util.SimpleAnimationListener

/**
 * Note: PagedList is content-immutable. This means that, although new content can be loaded into
 * an instance of PagedList, the loaded items themselves cannot change once loaded.
 * As such, if content in a PagedList updates, the PagedListAdapter object receives a
 * completely new PagedList that contains the updated information.
 *
 * Created by DoNguyen on 23/10/18.
 */
class MessagesAdapter(var listener: MessageViewHolder.OnAttachmentListener? = null)
    : PagedListAdapter<Message, MessageViewHolder>(COMPARATOR) {

    var selectionTracker: SelectionTracker<Long>? = null
    var isEnableSelection = true
    private var scaledDuration: Long = -1L

    override fun getItemViewType(position: Int): Int {
        val userId = getMessage(position)?.userId
        return when (userId) {
            1L -> R.layout.item_my_message
            else -> R.layout.item_their_message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // init the value of scaledDuration
        if (scaledDuration == -1L) {
            val scale = Settings.Global.getFloat(parent.context.contentResolver,
                    Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f)
            scaledDuration = (300 * scale).toLong()
        }
        return MessageViewHolder.create(parent, viewType, this, listener)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // message is only null when we enabled placeholder in the recycler view
        val message = getMessage(position) ?: return
        val isSelected = isEnableSelection && selectionTracker?.isSelected(message.id) ?: false
        holder.bind(message, position, isSelected)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, payloads: MutableList<Any>) {
        val attachmentPayload = payloads.find {
            it is Bundle && it.containsKey(KEY_DELETED_ATTACHMENT_ID)
        }
        if (attachmentPayload == null) {
            onBindViewHolder(holder, position)
            return
        }

        // for the item which the user just deleted an attachment from, we will run a scale animation,
        // and remove the AttachmentView when the animation ended
        val deletedAttachmentId = (attachmentPayload as Bundle).getString(KEY_DELETED_ATTACHMENT_ID)
        holder.attachmentsContainer.forEach {
            val attachmentView = it as AttachmentView
            if (attachmentView.attachment?.id == deletedAttachmentId) {
                val animation = AlphaAnimation(1f, 0f).apply {
                    duration = scaledDuration
                    fillAfter = true
                    setAnimationListener(object : SimpleAnimationListener {
                        override fun onAnimationEnd(animation: Animation?) {
                            holder.attachmentsContainer.removeView(attachmentView)
                            notifyItemChanged(holder.adapterPosition, mutableListOf<Any>())
                        }
                    })
                }
                attachmentView.startAnimation(animation)
            }
        }
    }

    override fun onViewRecycled(holder: MessageViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    /**
     * Get id of the item at an specified adapter position.
     *
     * @param [position] adapter position of the item
     * @return id of the item
     */
    override fun getItemId(position: Int): Long {
        // getItem will not be null, because we don't support placeholder
        return getItem(position)!!.id
    }

    /**
     * Get the message at a specified adapter position.
     *
     * @param [position] adapter position of the message
     * @return the [Message] at that position
     */
    fun getMessage(position: Int): Message? {
        return super.getItem(position)
    }

    /**
     * Get adapter position of a message.
     *
     * @param [messageId] id of the message
     * @return adapter position of the message
     */
    fun getAdapterPosition(messageId: Long): Int {
        currentList?.forEachIndexed { position, message ->
            if (message.id == messageId) return position
        }
        return RecyclerView.NO_POSITION
    }

    companion object {

        private const val KEY_DELETED_ATTACHMENT_ID = "key_deleted_attachment_id"

        private val COMPARATOR = object : DiffUtil.ItemCallback<Message>() {

            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem == newItem

            override fun getChangePayload(oldMessage: Message, newMessage: Message): Any? {
                // find the deleted attachment id
                oldMessage.attachments.forEach { oldAttachment ->
                    val isDeleted = newMessage.attachments.none { newAttachment ->
                        oldAttachment.id == newAttachment.id
                    }

                    if (isDeleted) {
                        val payload = Bundle()
                        payload.putString(KEY_DELETED_ATTACHMENT_ID, oldAttachment.id)
                        return payload
                    }
                }

                return null
            }
        }
    }
}