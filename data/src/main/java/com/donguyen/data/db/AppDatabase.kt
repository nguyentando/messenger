package com.donguyen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.donguyen.data.db.dao.AttachmentDao
import com.donguyen.data.db.dao.MessageDao
import com.donguyen.data.db.dao.UserDao
import com.donguyen.data.model.AttachmentData
import com.donguyen.data.model.MessageData
import com.donguyen.data.model.UserData

/**
 * Created by DoNguyen on 23/10/18.
 */
@Database(
        entities = [MessageData::class, AttachmentData::class, UserData::class],
        version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun userDao(): UserDao

    companion object {
        // these variables can change based on network condition
        var PAGE_SIZE = 20
        var PREFETCH_DISTANCE = 2 * PAGE_SIZE
        var INITIAL_LOAD_SIZE = 2 * PAGE_SIZE
    }
}