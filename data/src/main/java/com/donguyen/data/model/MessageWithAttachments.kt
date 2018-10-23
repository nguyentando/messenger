package com.donguyen.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessageWithAttachments {

    @Embedded
    var message: MessageData? = null

    // TODO - load all users into a shared HashMap for the whole app, instead of creating new user object for each message
    @Embedded(prefix = "from_user_")
    var user: UserData? = null

    @Relation(
            parentColumn = "id",
            entityColumn = "message_id"
    )
    var attachments: List<AttachmentData> = ArrayList()
}