package com.donguyen.domain.repository

import androidx.paging.PagedList
import com.donguyen.domain.model.Message
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
interface MessageRepository {

    /**
     * Get at messages in the database.
     * @return a [Result] of all messages with lazy loading supported by wrapping in a [PagedList]
     */
    fun getMessages(): Observable<Result<PagedList<Message>>>

    /**
     * Delete messages by ids.
     */
    fun deleteMessages(messageIds: Iterable<Long>): Observable<Result<None>>
}