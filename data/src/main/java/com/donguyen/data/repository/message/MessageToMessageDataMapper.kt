package com.donguyen.data.repository.message

import com.donguyen.data.model.MessageData
import com.donguyen.domain.model.Message
import com.donguyen.domain.util.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Singleton
class MessageToMessageDataMapper @Inject constructor() : Mapper<Message, MessageData>() {

    override fun mapFrom(from: Message): MessageData {
        return MessageData(
                id = from.id,
                userId = from.userId,
                content = from.content
        )
    }
}