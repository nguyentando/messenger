package com.donguyen.data.repository.attachment

import com.donguyen.data.db.dao.AttachmentDao
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
class AttachmentRepositoryImplTest {

    // sut
    private lateinit var attachmentRepositoryImpl: AttachmentRepositoryImpl

    // dependencies
    @Mock
    private lateinit var attachmentDao: AttachmentDao

    @Before
    fun setUp() {
        attachmentRepositoryImpl = AttachmentRepositoryImpl(attachmentDao)
    }

    @Test
    fun testDeleteAttachmentSucceeded() {
        // GIVEN attachmentDao return no error when deleting an attachment
        val attachmentId = "attachmentId"

        // WHEN deleting an attachment
        val testObserver = attachmentRepositoryImpl.deleteAttachment(attachmentId).test()

        // THEN delegate the logic to attachmentDao and return Result.success
        verify(attachmentDao).deleteById(attachmentId)
        testObserver
                .assertValue(Result.success(None()))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun testDeleteAttachmentError() {
        // GIVEN attachmentDao return error when deleting an attachment
        val attachmentId = "attachmentId"
        val throwable = Exception("delete attachment error")
        `when`(attachmentDao.deleteById(attachmentId))
                .then { throw throwable }

        // WHEN deleting an attachment
        val testObserver = attachmentRepositoryImpl.deleteAttachment(attachmentId).test()

        // THEN delegate the logic to attachmentDao and receive error
        verify(attachmentDao).deleteById(attachmentId)
        testObserver
                .assertError(throwable)
                .assertNoValues()
                .assertNotComplete()
    }
}