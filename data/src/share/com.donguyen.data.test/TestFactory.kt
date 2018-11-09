package com.donguyen.data.test

import com.donguyen.data.model.AttachmentData
import com.donguyen.data.model.MessageData
import com.donguyen.data.model.MessageWithAttachments
import com.donguyen.data.model.UserData
import com.donguyen.domain.model.Attachment
import com.donguyen.domain.model.User

/**
 * Created by DoNguyen on 28/10/18.
 */
object TestFactory {

    /* ---------------------------------------------------------------------------------------------
    * DATA
    * ------------------------------------------------------------------------------------------- */

    fun createAttachmentData(attachmentId: String, messageId: Long = 1) = AttachmentData(
            id = attachmentId,
            title = "title_$attachmentId",
            url = "url_$attachmentId",
            thumbnailUrl = "thumbnail_$attachmentId",
            messageId = messageId)

    fun createUserData(userId: Long) = UserData(
            id = userId,
            name = "name_$userId",
            avatarUrl = "avatarUrl_$userId")

    fun createMessageData(messageId: Long, userId: Long = 1) = MessageData(
            id = messageId,
            userId = userId,
            content = "content_$messageId")

    /**
     * @attachmentId null if don't want to create attachment object
     */
    fun createMessageWithAttachments(messageId: Long,
                                     userId: Long = messageId,
                                     attachmentId: String? = messageId.toString()) =
            MessageWithAttachments().apply {
                user = createUserData(userId)
                message = createMessageData(messageId, userId)
                attachments = if (attachmentId != null) {
                    val attachment = createAttachmentData(attachmentId, messageId)
                    arrayListOf(attachment)
                } else {
                    arrayListOf()
                }
            }

    /* ---------------------------------------------------------------------------------------------
    * DOMAIN
    * ------------------------------------------------------------------------------------------- */

    fun createAttachment(id: String, messageId: Long = 1) = Attachment(
            id = id,
            title = "title_$id",
            url = "url_$id",
            thumbnailUrl = "thumbnail_$id",
            messageId = messageId)

    fun createUser(userId: Long) = User(
            id = userId,
            name = "name_$userId",
            avatarUrl = "avatarUrl_$userId")

    /* ---------------------------------------------------------------------------------------------
    * PRESENTATION
    * ------------------------------------------------------------------------------------------- */
}