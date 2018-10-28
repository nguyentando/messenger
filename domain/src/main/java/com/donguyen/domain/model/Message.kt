package com.donguyen.domain.model

/**
 * Created by DoNguyen on 23/10/18.
 */
data class Message(var id: Long,
                   var userId: Long,
                   var content: String,
                   var attachments: List<Attachment> = listOf(),
                   var user: User)