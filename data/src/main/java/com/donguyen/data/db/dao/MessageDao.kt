package com.donguyen.data.db.dao

import androidx.room.Dao
import com.donguyen.data.model.MessageData

/**
 * Created by DoNguyen on 23/10/18.
 */
@Dao
interface MessageDao : BaseDao<MessageData>