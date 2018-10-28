package com.donguyen.data.repository.attachment

import com.donguyen.data.util.TestFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by DoNguyen on 28/10/18.
 */
class AttachmentDataToAttachmentMapperTest {

    // sut
    private val mapper = AttachmentDataToAttachmentMapper()

    @Test
    fun testReturnExpectedValue() {
        // GIVEN
        val attachmentData = TestFactory.createAttachmentData("attachmentId")

        // WHEN
        val attachment = mapper.mapFrom(attachmentData)

        // THEN
        assertEquals(attachment.id, attachmentData.id)
        assertEquals(attachment.title, attachmentData.title)
        assertEquals(attachment.url, attachmentData.url)
        assertEquals(attachment.thumbnailUrl, attachmentData.thumbnailUrl)
        assertEquals(attachment.messageId, attachmentData.messageId)
    }
}