package com.donguyen.data.repository.attachment

import com.donguyen.data.util.TestFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by DoNguyen on 28/10/18.
 */
class AttachmentToAttachmentDataMapperTest {

    // sut
    private val mapper = AttachmentToAttachmentDataMapper()

    @Test
    fun testReturnExpectedValue() {
        // GIVEN
        val attachment = TestFactory.createAttachment("attachmentId")

        // WHEN
        val attachmentData = mapper.mapFrom(attachment)

        // THEN
        assertEquals(attachmentData.id, attachment.id)
        assertEquals(attachmentData.title, attachment.title)
        assertEquals(attachmentData.url, attachment.url)
        assertEquals(attachmentData.thumbnailUrl, attachment.thumbnailUrl)
        assertEquals(attachmentData.messageId, attachment.messageId)
    }
}