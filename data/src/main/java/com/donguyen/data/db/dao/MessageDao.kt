package com.donguyen.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.donguyen.data.model.AttachmentData
import com.donguyen.data.model.MessageData
import com.donguyen.data.model.MessageWithAttachments

/**
 * Created by DoNguyen on 23/10/18.
 */
@Dao
interface MessageDao : BaseDao<MessageData> {

    /**
     * This query will tell Room to query both the [MessageData] and [AttachmentData] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query(
            "SELECT messages.*, " +
                    "users.id AS from_user_id, " +
                    "users.name AS from_user_name, " +
                    "users.avatar_url AS from_user_avatar_url " +
                    "FROM messages INNER JOIN users ON messages.user_id = users.id"
    )
    // TODO - check if the PositionalDataSource works well in this case
    fun getAllMessagesWithAttachments(): DataSource.Factory<Int, MessageWithAttachments>
}