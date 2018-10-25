package com.donguyen.domain.usecase.message

import androidx.paging.PagedList
import com.donguyen.domain.model.Message
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.UseCase
import com.donguyen.domain.util.None
import com.donguyen.domain.util.Transformer
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class GetMessagesUseCase(private val messageRepository: MessageRepository,
                         transformer: Transformer<Result<PagedList<Message>>>? = null)
    : UseCase<None, PagedList<Message>>(transformer) {

    override fun buildObservable(input: None): Observable<Result<PagedList<Message>>> {
        return messageRepository.getMessages()
    }
}