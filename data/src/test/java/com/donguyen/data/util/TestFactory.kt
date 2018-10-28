package com.donguyen.data.util

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

    fun createAttachmentData(attachmentId: String, messageId: Long = 1) =
            AttachmentData(
                    id = attachmentId,
                    title = "title_$attachmentId",
                    url = "url_$attachmentId",
                    thumbnailUrl = "thumbnail_$attachmentId",
                    messageId = messageId)

    fun createUserData(userId: Long) =
            UserData(
                    id = userId,
                    name = "name_$userId",
                    avatarUrl = "avatarUrl_$userId"
            )

    fun createMessageData(messageId: Long, userId: Long = 1) =
            MessageData(
                    id = messageId,
                    userId = userId,
                    content = "content_$messageId")

    fun createMessageWithAttachments(messageId: Long) =
            MessageWithAttachments().apply {
                user = createUserData(1)
                message = createMessageData(messageId, user!!.id)
                val attachment = createAttachmentData("attachmentId", messageId)
                attachments = arrayListOf(attachment)
            }

    /* ---------------------------------------------------------------------------------------------
    * DOMAIN
    * ------------------------------------------------------------------------------------------- */

    fun createAttachment(id: String, messageId: Long = 1) =
            Attachment(
                    id = id,
                    title = "title_$id",
                    url = "url_$id",
                    thumbnailUrl = "thumbnail_$id",
                    messageId = messageId)

    fun createUser(userId: Long) =
            User(
                    id = userId,
                    name = "name_$userId",
                    avatarUrl = "avatarUrl_$userId"
            )

    /* ---------------------------------------------------------------------------------------------
    * PRESENTATION
    * ------------------------------------------------------------------------------------------- */
}