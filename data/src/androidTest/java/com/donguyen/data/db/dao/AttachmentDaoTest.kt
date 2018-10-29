package com.donguyen.data.db.dao

import com.donguyen.data.test.TestFactory
import org.junit.Test

/**
 * Created by DoNguyen on 29/10/18.
 */
class AttachmentDaoTest : BaseDaoTest() {

    // sut
    private lateinit var attachmentDao: AttachmentDao

    // dependencies
    private lateinit var userDao: UserDao
    private lateinit var messageDao: MessageDao

    override fun setUp() {
        super.setUp()
        attachmentDao = appDatabase.attachmentDao()
        userDao = appDatabase.userDao()
        messageDao = appDatabase.messageDao()
    }

    @Test
    fun deleteNonExistentAttachment_return0() {
        // GIVEN the database is empty

        // WHEN
        val deletedCount = attachmentDao.deleteById("attachmentId")

        // THEN
        check(deletedCount == 0)
    }

    @Test
    fun deleteExistentAttachment_return1() {
        // GIVEN
        val user = TestFactory.createUserData(1)
        val message = TestFactory.createMessageData(messageId = 1, userId = user.id)
        val attachment = TestFactory.createAttachmentData(attachmentId = "attachmentId", messageId = message.id)

        userDao.insertItem(user)
        messageDao.insertItem(message)
        attachmentDao.insertItem(attachment)

        // WHEN
        val deletedCount = attachmentDao.deleteById(attachment.id)

        // THEN
        check(deletedCount == 1)
    }
}