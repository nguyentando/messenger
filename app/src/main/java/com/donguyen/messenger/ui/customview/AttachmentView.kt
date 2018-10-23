package com.donguyen.messenger.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
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
) : LinearLayout(context, attrs, defStyleAttr) {

    var attachment: Attachment? = null

    private val attachmentImg: ImageView
    private val attachmentTitleTxt: TextView

    init {
        orientation = VERTICAL
        setBackgroundResource(R.drawable.bg_gray_stroke_corner)
        inflate(context, R.layout.view_attachment_image, this)
        inflate(context, R.layout.view_attachment_title, this)
        attachmentImg = findViewById(R.id.attachment_img)
        attachmentTitleTxt = findViewById(R.id.attachment_title_txt)
    }

    fun updateAttachment(attachment: Attachment) {
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