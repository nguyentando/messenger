package com.donguyen.domain.usecase.message

import com.donguyen.domain.model.Message
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.UseCase
import com.donguyen.domain.usecase.message.DeleteMessagesUseCase.Input
import com.donguyen.domain.util.Transformer
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class DeleteMessagesUseCase(transformer: Transformer<Result<Boolean>>,
                            private val messageRepository: MessageRepository)
    : UseCase<Input, Boolean>(transformer) {

    override fun buildObservable(input: Input): Observable<Result<Boolean>> {
        return messageRepository.deleteMessages(input.messages)
    }

    data class Input(val messages: List<Message>)
}