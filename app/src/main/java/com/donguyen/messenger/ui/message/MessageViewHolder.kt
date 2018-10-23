package com.donguyen.messenger.ui.message

import android.content.Context
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.domain.model.Attachment
import com.donguyen.domain.model.Message
import com.donguyen.messenger.R
import com.donguyen.messenger.ui.customview.AttachmentView
import com.donguyen.messenger.ui.message.selection.MessageItemDetails
import com.donguyen.messenger.util.GlideApp
import java.util.*


/**
 * Created by DoNguyen on 23/10/18.
 */
abstract class MessageViewHolder(view: View,
                                 private val adapter: MessagesAdapter,
                                 var listener: OnDeleteAttachmentListener? = null)
    : RecyclerView.ViewHolder(view) {

    private val contentTxt: TextView = view.findViewById(R.id.content_txt)
    private val attachmentsContainer: LinearLayout = view.findViewById(R.id.attachments_container)

    private val meString = view.context.getString(R.string.me)
    private val menuItems = arrayOf<CharSequence>(view.context.getString(R.string.delete_attachment))

    private val attachmentViewPool = AttachmentViewPool(view.context)

    open fun bind(message: Message, position: Int, isActivated: Boolean) {
        itemView.isActivated = isActivated

        // TODO - check again on how to handle special characters
        // merge user name and message content
        val userName = if (message.user.id == 1L) meString else message.user.name
        val contentFormatted = message.content.replace("\n", "")
        val contentFull = itemView.context.getString(R.string.message_format, userName, contentFormatted)

        // apply bold span on user name
        val sb = SpannableStringBuilder(contentFull)
        val bss = StyleSpan(BOLD)
        val endNamePos = contentFull.indexOf('\n')
        sb.setSpan(bss, 0, endNamePos, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        contentTxt.text = sb

        // create enough AttachmentView in the container
        val viewCount = attachmentsContainer.childCount
        val attachmentCount = message.attachments.size
        var different = Math.abs(viewCount - attachmentCount)
        when {
            viewCount > attachmentCount -> {
                // cache AttachmentView(s) into the pool
                while (different > 0) {
                    val attachmentView = attachmentsContainer.getChildAt(0) as AttachmentView
                    attachmentsContainer.removeViewAt(0)
                    attachmentViewPool.add(attachmentView)
                    different--
                }
            }

            viewCount < attachmentCount -> {
                // reuse AttachmentView(s) from the pool
                while (different > 0) {
                    val attachmentView = attachmentViewPool.getAttachmentView(itemView.context)
                    attachmentsContainer.addView(attachmentView)
                    different--
                }
            }
        }

        // bind attachment data into AttachmentView
        for ((index, attachment) in message.attachments.withIndex()) {
            val attachmentView = attachmentsContainer.getChildAt(index) as AttachmentView
            attachmentView.apply {
                updateAttachment(attachment)
                setOnLongClickListener {
                    // TODO - prevent recycler view from listening to this event and turn on selection mode
                    AlertDialog.Builder(context)
                            .setItems(menuItems) { _, _ ->
                                listener?.onDeleteAttachment(attachment)
                            }
                            .show()
                    true
                }
            }
        }

        // TODO - save the attachment image ratio into the database to use later
    }

    fun getMessageItemDetails(): ItemDetailsLookup.ItemDetails<Long> {
        return MessageItemDetails(adapterPosition, adapter.getMessage(adapterPosition)!!)
    }

    open fun clear() {
        attachmentsContainer.forEach { view ->
            (view as AttachmentView).clear()
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int,
                   adapter: MessagesAdapter,
                   listener: OnDeleteAttachmentListener? = null): MessageViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(viewType, parent, false)

            return when (viewType) {
                R.layout.item_my_message -> MyMessageViewHolder(view, adapter, listener)
                else -> TheirMessageViewHolder(view, adapter, listener)
            }
        }
    }

    /**
     * A shared pool of AttachmentView. Use it when display attachments to improve performance.
     */
    private class AttachmentViewPool(context: Context) : Stack<AttachmentView>() {

        private val marginTop = context.resources.getDimensionPixelSize(R.dimen.margin_normal)

        fun getAttachmentView(context: Context): AttachmentView {
            return when {
                size > 0 -> pop()
                else -> AttachmentView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        topMargin = marginTop
                    }
                }
            }
        }
    }

    interface OnDeleteAttachmentListener {
        fun onDeleteAttachment(attachment: Attachment)
    }
}

class MyMessageViewHolder(view: View, adapter: MessagesAdapter,
                          listener: OnDeleteAttachmentListener? = null)
    : MessageViewHolder(view, adapter, listener)

class TheirMessageViewHolder(view: View, adapter: MessagesAdapter,
                             listener: OnDeleteAttachmentListener? = null)
    : MessageViewHolder(view, adapter, listener) {

    private val avatarImg: ImageView = view.findViewById(R.id.avatar_img)

    override fun bind(message: Message, position: Int, isActivated: Boolean) {
        super.bind(message, position, isActivated)
        GlideApp.with(avatarImg.context)
                .load(message.user.avatarUrl)
                .placeholder(R.drawable.bg_gray_solid_circle)
                .circleCrop()
                .into(avatarImg)
    }

    override fun clear() {
        super.clear()
        avatarImg.setImageDrawable(null)
    }
}