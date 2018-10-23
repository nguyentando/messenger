package com.donguyen.messenger.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donguyen.domain.model.Message
import com.donguyen.messenger.R
import com.donguyen.messenger.ui.customview.AttachmentView
import com.donguyen.messenger.util.GlideApp


/**
 * Created by DoNguyen on 23/10/18.
 */
abstract class MessageViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {

    protected val nameTxt: TextView = view.findViewById(R.id.name_txt)
    private val contentTxt: TextView = view.findViewById(R.id.content_txt)
    private val attachmentsContainer: LinearLayout = view.findViewById(R.id.attachments_container)

    private val marginTop = view.context.resources.getDimensionPixelSize(R.dimen.margin_normal)

    open fun bind(message: Message, position: Int) {
        // TODO - check again on how to handle special characters
        contentTxt.text = message.content.replace("\n", "", true)

        // TODO - write a ViewPool to reuse AttachmentView instances
        // remove all attachment views
        attachmentsContainer.removeAllViews()

        // create new attachment views
        for (attachment in message.attachments) {
            val attachmentView = AttachmentView(attachmentsContainer.context).apply {
                setAttachment(attachment)
            }
            val params = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = marginTop
            }
            attachmentsContainer.addView(attachmentView, params)
        }

        // TODO - save the attachment image ratio into the database to use later
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(viewType, parent, false)

            return when (viewType) {
                R.layout.item_my_message -> MyMessageViewHolder(view)
                else -> TheirMessageViewHolder(view)
            }
        }
    }
}

class MyMessageViewHolder(view: View) : MessageViewHolder(view) {

    override fun bind(message: Message, position: Int) {
        super.bind(message, position)
        nameTxt.setText(R.string.me)
    }
}

class TheirMessageViewHolder(view: View) : MessageViewHolder(view) {

    private val avatarImg: ImageView = view.findViewById(R.id.avatar_img)

    override fun bind(message: Message, position: Int) {
        super.bind(message, position)
        nameTxt.text = message.user.name
        GlideApp.with(avatarImg.context)
                .load(message.user.avatarUrl)
                .placeholder(R.drawable.bg_gray_solid_circle)
                .circleCrop()
                .into(avatarImg)
    }
}