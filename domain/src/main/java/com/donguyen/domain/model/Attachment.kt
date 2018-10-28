package com.donguyen.domain.model

/**
 * Created by DoNguyen on 23/10/18.
 */
data class Attachment(var id: String,
                      var title: String,
                      var url: String,
                      var thumbnailUrl: String,
                      var messageId: Long)