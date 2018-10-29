package com.donguyen.data.repository.message

import com.donguyen.data.db.dao.MessageDao
import com.donguyen.data.repository.attachment.AttachmentDataToAttachmentMapper
import com.donguyen.data.repository.user.UserDataToUserMapper
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by DoNguyen on 28/10/18.
 */
@RunWith(MockitoJUnitRunner::class)
class MessageRepositoryImplTest {

    // sut
    private lateinit var messageRepositoryImpl: MessageRepositoryImpl

    // dependencies
    @Mock
    private lateinit var messageDao: MessageDao
    private val messageWithAttachmentsToMessageMapper = MessageWithAttachmentsToMessageMapper(
            AttachmentDataToAttachmentMapper(), UserDataToUserMapper())

    @Before
    fun setUp() {
        messageRepositoryImpl = MessageRepositoryImpl(messageDao, messageWithAttachmentsToMessageMapper)
    }

    /* ---------------------------------------------------------------------------------------------
    * DELETE MESSAGES
    * ------------------------------------------------------------------------------------------- */

    @Test
    fun deleteMessagesSucceeded() {
        // GIVEN messageDao return no error when deleting messages
        val messageIds = arrayListOf(1L)

        // WHEN deleting messages
        val testObserver = messageRepositoryImpl.deleteMessages(messageIds).test()

        // THEN delegate the logic to messageDao and return Result.success
        verify(messageDao).deleteMessages(messageIds)
        testObserver
                .assertValue(Result.success(None()))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun insertUsersError() {
        // GIVEN messageDao return error when deleting messages
        val messageIds = arrayListOf(1L)
        val throwable = Throwable("delete messages error")
        `when`(messageDao.deleteMessages(messageIds))
                .then { throw throwable }

        // WHEN deleting messages
        val testObserver = messageRepositoryImpl.deleteMessages(messageIds).test()

        // THEN delegate the logic to messageDao and receive error
        verify(messageDao).deleteMessages(messageIds)
        testObserver
                .assertError(throwable)
                .assertNoValues()
                .assertNotComplete()
    }

    /* ---------------------------------------------------------------------------------------------
    * GET MESSAGES
    * ------------------------------------------------------------------------------------------- */
    // TODO - write unit tests for MessageRepositoryImpl.getMessages()
}