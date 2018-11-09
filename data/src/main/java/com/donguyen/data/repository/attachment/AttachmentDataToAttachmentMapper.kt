package com.donguyen.data.repository.attachment

import com.donguyen.data.model.AttachmentData
import com.donguyen.domain.model.Attachment
import com.donguyen.domain.util.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Singleton
class AttachmentDataToAttachmentMapper @Inject constructor() : Mapper<AttachmentData, Attachment> {

    override fun mapFrom(from: AttachmentData): Attachment {
        return Attachment(
                id = from.id,
                title = from.title,
                url = from.url,
                thumbnailUrl = from.thumbnailUrl,
                messageId = from.messageId
        )
    }
}