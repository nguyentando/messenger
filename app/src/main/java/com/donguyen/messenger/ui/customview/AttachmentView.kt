package com.donguyen.messenger.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.donguyen.domain.model.Attachment
import com.donguyen.messenger.R
import com.donguyen.messenger.util.GlideApp


/**
 * Created by DoNguyen on 23/10/18.
 */
class AttachmentView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var attachment: Attachment? = null

    private val attachmentImg: ImageView
    private val attachmentTitleTxt: TextView

    init {
        // TODO - inflate attachmentImg and attachmentTitleTxt directly to AttachmentView
        inflate(context, R.layout.view_attachment, this)
        attachmentImg = findViewById(R.id.attachment_img)
        attachmentTitleTxt = findViewById(R.id.attachment_title_txt)
    }

    fun setAttachment(attachment: Attachment) {
        if (this.attachment == attachment) return
        this.attachment = attachment
        updateContent()
    }

    private fun updateContent() {
        // TODO - pick between thumbnailUrl and url based on network condition
        GlideApp.with(context)
                .load(attachment?.url)
                .placeholder(R.drawable.bg_gray_solid_corner)
                .centerCrop()
                .into(attachmentImg)

        attachmentTitleTxt.text = attachment?.title
    }
}