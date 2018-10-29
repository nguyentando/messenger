package com.donguyen.messenger.ui.message

import androidx.lifecycle.Observer
import com.donguyen.domain.model.Message
import com.donguyen.domain.test.createMockPagedList
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.usecase.attachment.DeleteAttachmentUseCase
import com.donguyen.domain.usecase.message.DeleteMessagesUseCase
import com.donguyen.domain.usecase.message.GetMessagesUseCase
import com.donguyen.domain.util.None
import com.donguyen.messenger.base.BaseViewModel
import com.donguyen.messenger.base.DeleteAttachmentSuccess
import com.donguyen.messenger.base.DeleteMessagesSuccess
import com.donguyen.messenger.ui.BaseViewModelTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Created by DoNguyen on 29/10/18.
 */
class MessagesViewModelTest : BaseViewModelTest() {

    // sut
    private lateinit var messagesViewModel: MessagesViewModel

    // dependencies
    @Mock
    private lateinit var getMessagesUseCase: GetMessagesUseCase
    @Mock
    private lateinit var deleteMessagesUseCase: DeleteMessagesUseCase
    @Mock
    private lateinit var deleteAttachmentUseCase: DeleteAttachmentUseCase

    // checkers
    private lateinit var viewObserver: Observer<MessagesViewState>

    override fun viewModel(): BaseViewModel {
        return messagesViewModel
    }

    @Before
    @Suppress("UNCHECKED_CAST")
    override fun setUp() {
        viewObserver = mock(Observer::class.java) as Observer<MessagesViewState>
        messagesViewModel = MessagesViewModel(getMessagesUseCase, deleteMessagesUseCase, deleteAttachmentUseCase)
        messagesViewModel.viewState.observeForever(viewObserver)
        super.setUp()
    }

    @Test
    fun init() {
        // WHEN the messagesViewModel is being initialized
        // THEN receive a MessagesViewState instance with default constructor params
        verify(viewObserver).onChanged(MessagesViewState())
        verifyZeroInteractions(viewObserver)
    }

    @Test
    fun loadMessagesSucceeded() {
        // GIVEN getMessagesUseCase return success
        val result = Result.success(createMockPagedList<Message>())
        `when`(getMessagesUseCase.execute(None()))
                .thenReturn(Observable.just(result))

        // WHEN getMessages
        messagesViewModel.loadMessages()

        // THEN receive loading then receive success with correct data
        verify(viewObserver).onChanged(MessagesViewState(loading = true, error = ""))
        verify(viewObserver).onChanged(MessagesViewState(
                loading = false,
                messages = result.data,
                error = ""))
    }

    @Test
    fun loadMessagesFailed() {
        // GIVEN getMessagesUseCase return failure
        val error = "get messages failed"
        `when`(getMessagesUseCase.execute(None()))
                .thenReturn(Observable.just(Result.failure(error)))

        // WHEN getMessages
        messagesViewModel.loadMessages()

        // THEN receive loading then receive failure with correct error message
        verify(viewObserver).onChanged(MessagesViewState(loading = true, error = ""))
        verify(viewObserver).onChanged(
                messagesViewModel.viewState.value?.copy(loading = false, error = error))
    }

    @Test
    fun deleteMessagesSucceeded() {
        // GIVEN deleteMessagesUseCase return success
        val input = DeleteMessagesUseCase.Input(arrayListOf())
        `when`(deleteMessagesUseCase.execute(input))
                .thenReturn(Observable.just(Result.success(None())))

        // WHEN deleteMessages
        messagesViewModel.deleteMessages(input.messageIds)

        // THEN receive DeleteMessagesSuccess event
        verify(eventObserver).onChanged(DeleteMessagesSuccess())
    }

    @Test
    fun deleteMessagesFailed() {
        // GIVEN deleteMessagesUseCase return failure
        val input = DeleteMessagesUseCase.Input(arrayListOf())
        val error = "delete messages failed"
        `when`(deleteMessagesUseCase.execute(input))
                .thenReturn(Observable.just(Result.failure(error)))

        // WHEN deleteMessages
        messagesViewModel.deleteMessages(input.messageIds)

        // THEN receive failure with correct error message
        verify(viewObserver).onChanged(
                messagesViewModel.viewState.value?.copy(loading = false, error = error))
    }

    @Test
    fun deleteAttachmentSucceeded() {
        // GIVEN deleteAttachmentUseCase return success
        val input = DeleteAttachmentUseCase.Input("attachmentId")
        `when`(deleteAttachmentUseCase.execute(input))
                .thenReturn(Observable.just(Result.success(None())))

        // WHEN deleteAttachment
        messagesViewModel.deleteAttachment(input.attachmentId)

        // THEN receive DeleteAttachmentSuccess event
        verify(eventObserver).onChanged(DeleteAttachmentSuccess())
    }

    @Test
    fun deleteAttachmentFailed() {
        // GIVEN deleteAttachmentUseCase return failure
        val input = DeleteAttachmentUseCase.Input("attachmentId")
        val error = "delete attachment failed"
        `when`(deleteAttachmentUseCase.execute(input))
                .thenReturn(Observable.just(Result.failure(error)))

        // WHEN deleteAttachment
        messagesViewModel.deleteAttachment(input.attachmentId)

        // THEN receive failure with correct error attachment
        verify(viewObserver).onChanged(
                messagesViewModel.viewState.value?.copy(loading = false, error = error))
    }
}