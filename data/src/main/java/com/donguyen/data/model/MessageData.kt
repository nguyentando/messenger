package com.donguyen.data.model

import androidx.room.*

/**
 * Created by DoNguyen on 23/10/18.
 */
@Entity(
        tableName = "messages",
        foreignKeys = [
            ForeignKey(
                    entity = UserData::class, parentColumns = ["id"], childColumns = ["user_id"],
                    onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
            )],
        indices = [Index("user_id")] // index this to avoid full table scans when the users table is modified
)
data class MessageData(

        @PrimaryKey
        val id: Long,

        @ColumnInfo(name = "user_id")
        val userId: Long,

        val content: String
) {

    // only use this when parsing data from the json file to object
    @Ignore
    val attachments: List<AttachmentData>? = listOf()
}