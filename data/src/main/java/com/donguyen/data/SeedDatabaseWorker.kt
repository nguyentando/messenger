package com.donguyen.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.donguyen.data.db.AppDatabase
import com.donguyen.data.model.MessageData
import com.donguyen.data.model.UserData
import com.google.gson.Gson
import com.google.gson.stream.JsonReader

/**
 * Created by DoNguyen on 23/10/18.
 */
class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    companion object {
        private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }
    }

    override fun doWork(): Result {
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open("data.json")
            jsonReader = JsonReader(inputStream.reader())
            val data: Data = Gson().fromJson(jsonReader, Data::class.java)

            val database = AppDatabase.getInstance(applicationContext)

            // insert users
            val userDao = database.userDao()
            userDao.insertItems(data.users)

            // insert messages
            val messageDao = database.messageDao()
            messageDao.insertItems(data.messages)

            // insert attachments
            val attachmentDao = database.attachmentDao()
            data.messages.forEach { message ->
                if (message.attachments != null) {
                    message.attachments.forEach { attachment ->
                        attachment.messageId = message.id

                        // don't know why Glide (and Picasso) can not load http urls.
                        // https is fine.
                        // use a temporary fix here. TODO - will look into it more
                        attachment.url = attachment.url.replace("http", "https")
                        attachment.thumbnailUrl = attachment.thumbnailUrl.replace("http", "https")
                    }
                    attachmentDao.insertItems(message.attachments)
                }
            }

            Result.SUCCESS

        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.FAILURE
        } finally {
            jsonReader?.close()
        }
    }
}

private data class Data(
        val users: List<UserData>,
        val messages: List<MessageData>
)