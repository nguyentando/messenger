package com.donguyen.messenger.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.donguyen.domain.model.Attachment
import com.donguyen.messenger.R
import com.donguyen.messenger.util.GlideApp
import com.google.android.material.card.MaterialCardView


/**
 * Created by DoNguyen on 23/10/18.
 */
class AttachmentView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    var attachment: Attachment? = null

    private val attachmentImg: ImageView
    private val attachmentTitleTxt: TextView

    init {
        radius = context.resources.getDimensionPixelSize(R.dimen.corner_size).toFloat()
        elevation = context.resources.getDimensionPixelSize(R.dimen.attachment_card_elevation).toFloat()
        cardElevation = elevation
        useCompatPadding = true

        inflate(context, R.layout.view_attachment, this)
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

    fun clear() {
        attachment = null
        attachmentImg.setImageDrawable(null)
    }
}