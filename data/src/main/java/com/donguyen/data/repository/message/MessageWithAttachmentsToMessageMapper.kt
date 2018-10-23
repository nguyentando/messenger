package com.donguyen.data.repository.message

import com.donguyen.data.model.MessageWithAttachments
import com.donguyen.data.repository.attachment.AttachmentDataToAttachmentMapper
import com.donguyen.data.repository.user.UserDataToUserMapper
import com.donguyen.domain.model.Message
import com.donguyen.domain.util.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Singleton
class MessageWithAttachmentsToMessageMapper @Inject constructor(
        private val attachmentDataToAttachmentMapper: AttachmentDataToAttachmentMapper,
        private val userDataToUserMapper: UserDataToUserMapper
) : Mapper<MessageWithAttachments, Message>() {

    override fun mapFrom(from: MessageWithAttachments): Message {
        return Message(
                id = from.message!!.id,
                content = from.message!!.content,
                userId = from.message!!.userId,
                attachments = attachmentDataToAttachmentMapper.mapFromList(from.attachments),
                user = userDataToUserMapper.mapFrom(from.user!!)
        )
    }
}