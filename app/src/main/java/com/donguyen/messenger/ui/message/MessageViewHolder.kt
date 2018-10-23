package com.donguyen.messenger.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.domain.model.Attachment
import com.donguyen.domain.model.Message
import com.donguyen.messenger.R
import com.donguyen.messenger.ui.customview.AttachmentView
import com.donguyen.messenger.ui.message.selection.MessageItemDetails
import com.donguyen.messenger.util.GlideApp


/**
 * Created by DoNguyen on 23/10/18.
 */
abstract class MessageViewHolder(view: View,
                                 private val adapter: MessagesAdapter,
                                 var listener: OnDeleteAttachmentListener? = null)
    : RecyclerView.ViewHolder(view) {

    protected val nameTxt: TextView = view.findViewById(R.id.name_txt)
    private val contentTxt: TextView = view.findViewById(R.id.content_txt)
    private val attachmentsContainer: LinearLayout = view.findViewById(R.id.attachments_container)

    private val marginTop = view.context.resources.getDimensionPixelSize(R.dimen.margin_normal)
    private val menuItems = arrayOf<CharSequence>(view.context.getString(R.string.delete_attachment))

    open fun bind(message: Message, position: Int, isActivated: Boolean) {
        itemView.isActivated = isActivated
        // TODO - check again on how to handle special characters
        contentTxt.text = message.content.replace("\n", "", true)

        // TODO - write a ViewPool to reuse AttachmentView instances
        // remove all attachment views
        attachmentsContainer.removeAllViews()

        // create new attachment views
        for (attachment in message.attachments) {
            val attachmentView = AttachmentView(attachmentsContainer.context).apply {
                updateAttachment(attachment)
                setOnLongClickListener {
                    AlertDialog.Builder(context)
                            .setItems(menuItems) { _, _ ->
                                listener?.onDeleteAttachment(attachment)
                            }
                            .show()
                    true
                }
            }
            val params = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = marginTop
            }
            attachmentsContainer.addView(attachmentView, params)
        }

        // TODO - save the attachment image ratio into the database to use later
    }

    fun getMessageItemDetails(): ItemDetailsLookup.ItemDetails<Long> {
        return MessageItemDetails(adapterPosition, adapter.getMessage(adapterPosition)!!)
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

    interface OnDeleteAttachmentListener {
        fun onDeleteAttachment(attachment: Attachment)
    }
}

class MyMessageViewHolder(view: View, adapter: MessagesAdapter,
                          listener: OnDeleteAttachmentListener? = null)
    : MessageViewHolder(view, adapter, listener) {

    override fun bind(message: Message, position: Int, isActivated: Boolean) {
        super.bind(message, position, isActivated)
        nameTxt.setText(R.string.me)
    }
}

class TheirMessageViewHolder(view: View, adapter: MessagesAdapter,
                             listener: OnDeleteAttachmentListener? = null)
    : MessageViewHolder(view, adapter, listener) {

    private val avatarImg: ImageView = view.findViewById(R.id.avatar_img)

    override fun bind(message: Message, position: Int, isActivated: Boolean) {
        super.bind(message, position, isActivated)
        nameTxt.text = message.user.name
        GlideApp.with(avatarImg.context)
                .load(message.user.avatarUrl)
                .placeholder(R.drawable.bg_gray_solid_circle)
                .circleCrop()
                .into(avatarImg)
    }
}