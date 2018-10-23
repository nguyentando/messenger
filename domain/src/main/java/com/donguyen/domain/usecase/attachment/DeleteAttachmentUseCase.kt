package com.donguyen.domain.usecase.attachment

import com.donguyen.domain.repository.AttachmentRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.UseCase
import com.donguyen.domain.usecase.attachment.DeleteAttachmentUseCase.Input
import com.donguyen.domain.util.Transformer
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class DeleteAttachmentUseCase(transformer: Transformer<Result<Boolean>>,
                              private val attachmentRepository: AttachmentRepository)
    : UseCase<Input, Boolean>(transformer) {

    override fun buildObservable(input: Input): Observable<Result<Boolean>> {
        return attachmentRepository.deleteAttachment(input.attachmentId)
    }

    data class Input(val attachmentId: String)
}