package com.donguyen.data.db.dao

import androidx.paging.PositionalDataSource
import androidx.room.paging.LimitOffsetDataSource
import com.donguyen.data.model.MessageWithAttachments
import com.donguyen.data.test.TestFactory
import org.junit.Test

/**
 * Created by DoNguyen on 29/10/18.
 */
class MessageDaoTest : BaseDaoTest() {

    // sut
    private lateinit var messageDao: MessageDao

    // dependencies
    private lateinit var userDao: UserDao

    override fun setUp() {
        super.setUp()
        messageDao = appDatabase.messageDao()
        userDao = appDatabase.userDao()
    }

    /* ---------------------------------------------------------------------------------------------
    * GET MESSAGES WITH ATTACHMENTS
    * ------------------------------------------------------------------------------------------- */

    @Test
    fun getMessages_GivenEmptyDatabase_return0() {
        // GIVEN the database is empty

        // WHEN
        val result = this.messageDao.getAllMessagesWithAttachments()

        // THEN
        check((result.create() as LimitOffsetDataSource).countItems() == 0)
    }

    @Test
    fun getMessages_GivenNonEmptyDatabase_returnTotalNumberOfRows() {
        // GIVEN the database is not empty
        val messages = mutableListOf<MessageWithAttachments>()
        for (messageId in 1L..2L) {
            // insert the message into database
            insertMessage(messageId)

            // create the MessageWithAttachments object according to this message
            messages.add(TestFactory.createMessageWithAttachments(messageId = messageId, attachmentId = null))
        }

        // WHEN
        val result = this.messageDao.getAllMessagesWithAttachments()

        // THEN
        val dataSource = result.create() as LimitOffsetDataSource
        val count = dataSource.countItems()
        val params = PositionalDataSource.LoadRangeParams(0, count)
        dataSource.loadRange(params, object : PositionalDataSource.LoadRangeCallback<MessageWithAttachments>() {
            override fun onResult(data: MutableList<MessageWithAttachments>) {
                check(count == messages.size)
                check(data == messages)
            }
        })
    }

    /* ---------------------------------------------------------------------------------------------
    * DELETE MESSAGES
    * ------------------------------------------------------------------------------------------- */

    @Test
    fun deleteNonExistentMessages_return0() {
        // GIVEN the database is empty

        // WHEN
        val messageIds = arrayListOf(1L, 2L)
        val deletedCount = this.messageDao.deleteMessages(messageIds)

        // THEN
        check(deletedCount == 0)
    }

    @Test
    fun deleteAnExistentMessage_return1() {
        // GIVEN
        val messageId = 1L
        insertMessage(1)

        // WHEN
        val deletedCount = this.messageDao.deleteMessages(arrayListOf(messageId))

        // THEN
        check(deletedCount == 1)
    }

    @Test
    fun deleteMessagesWithOnlyOneExistent_return1() {
        // GIVEN
        val messageIds = arrayListOf(1L, 2L)
        // only insert the first message into the database
        insertMessage(messageIds[0])

        // WHEN
        val deletedCount = this.messageDao.deleteMessages(messageIds)

        // THEN
        check(deletedCount == 1)
    }

    private fun insertMessage(messageId: Long, userId: Long = messageId) {
        val user = TestFactory.createUserData(userId)
        val message = TestFactory.createMessageData(messageId = messageId, userId = userId)
        userDao.insertItem(user)
        messageDao.insertItem(message)
    }
}