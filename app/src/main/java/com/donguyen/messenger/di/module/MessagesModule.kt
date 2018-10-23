package com.donguyen.messenger.di.module

import com.donguyen.domain.repository.AttachmentRepository
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.usecase.attachment.DeleteAttachmentUseCase
import com.donguyen.domain.usecase.message.DeleteMessagesUseCase
import com.donguyen.domain.usecase.message.GetMessagesUseCase
import com.donguyen.messenger.ui.message.MessagesVMFactory
import com.donguyen.messenger.util.rx.AsyncTransformer
import dagger.Module
import dagger.Provides

/**
 * Created by DoNguyen on 23/10/18.
 */
@Module
class MessagesModule {

    @Provides
    fun provideMessagesVMFactory(getMessagesUseCase: GetMessagesUseCase,
                                 deleteMessagesUseCase: DeleteMessagesUseCase,
                                 deleteAttachmentUseCase: DeleteAttachmentUseCase)
            : MessagesVMFactory {
        return MessagesVMFactory(getMessagesUseCase, deleteMessagesUseCase, deleteAttachmentUseCase)
    }

    @Provides
    fun provideGetMessagesUseCase(messageRepository: MessageRepository): GetMessagesUseCase {
        return GetMessagesUseCase(messageRepository, AsyncTransformer())
    }

    @Provides
    fun provideDeleteMessagesUseCase(messageRepository: MessageRepository): DeleteMessagesUseCase {
        return DeleteMessagesUseCase(messageRepository, AsyncTransformer())
    }

    @Provides
    fun provideDeleteAttachmentUseCase(attachmentRepository: AttachmentRepository): DeleteAttachmentUseCase {
        return DeleteAttachmentUseCase(attachmentRepository, AsyncTransformer())
    }
}