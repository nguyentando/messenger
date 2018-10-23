package com.donguyen.data.model

import androidx.room.*

/**
 * Created by DoNguyen on 23/10/18.
 */
@Entity(
    tableName = "attachments",
    foreignKeys = [ForeignKey(
        entity = MessageData::class, parentColumns = ["id"], childColumns = ["message_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("message_id")] // index this to avoid full table scans when the messages table is modified
)
data class AttachmentData(

    @PrimaryKey
    val id: String,

    val title: String,

    val url: String,

    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,

    @ColumnInfo(name = "message_id")
    var messageId: Long
)