package com.donguyen.data.repository.message

import com.donguyen.data.repository.attachment.AttachmentDataToAttachmentMapper
import com.donguyen.data.repository.user.UserDataToUserMapper
import com.donguyen.data.test.TestFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by DoNguyen on 28/10/18.
 */
class MessageWithAttachmentsToMessageMapperTest {

    // dependencies
    private val attachmentDataToAttachmentMapper = AttachmentDataToAttachmentMapper()
    private val userDataToUserMapper = UserDataToUserMapper()

    // sut
    private val mapper = MessageWithAttachmentsToMessageMapper(
            attachmentDataToAttachmentMapper, userDataToUserMapper)

    @Test
    fun returnExpectedValue() {
        // GIVEN
        val from = TestFactory.createMessageWithAttachments(1)

        // WHEN
        val to = mapper.mapFrom(from)

        // THEN
        assertEquals(to.id, from.message!!.id)
        assertEquals(to.content, from.message!!.content)
        assertEquals(to.userId, from.message!!.userId)
        assertEquals(to.attachments, attachmentDataToAttachmentMapper.mapFromList(from.attachments))
        assertEquals(to.user, userDataToUserMapper.mapFrom(from.user!!))
    }
}