package com.donguyen.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.donguyen.data.model.AttachmentData

/**
 * Created by DoNguyen on 23/10/18.
 */
@Dao
interface AttachmentDao : BaseDao<AttachmentData> {

    @Query("DELETE FROM attachments WHERE id = :attachmentId")
    fun deleteById(attachmentId: String)
}