package com.donguyen.domain.usecase.message

import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.UseCase
import com.donguyen.domain.usecase.message.DeleteMessagesUseCase.Input
import com.donguyen.domain.util.None
import com.donguyen.domain.util.Transformer
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class DeleteMessagesUseCase(private val messageRepository: MessageRepository,
                            transformer: Transformer<Result<None>>? = null)
    : UseCase<Input, None>(transformer) {

    override fun buildObservable(input: Input): Observable<Result<None>> {
        return messageRepository.deleteMessages(input.messageIds)
    }

    data class Input(val messageIds: Iterable<Long>)
}