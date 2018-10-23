package com.donguyen.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.donguyen.data.SeedDatabaseWorker
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

        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        // create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "messenger-db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // after the first run
                            // if the user cleared app data (by going to Settings/Apps)
                            // the database will be recreated and pre-populate data again
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance().enqueue(request)
                        }
                    })
                    .build()
        }
    }
}