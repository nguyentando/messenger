package com.donguyen.domain.usecase.message

import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.test.TestTransformer
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by DoNguyen on 28/10/18.
 */
@RunWith(MockitoJUnitRunner::class)
class DeleteMessagesUseCaseTest {

    // sut
    private lateinit var deleteMessagesUseCase: DeleteMessagesUseCase

    // dependencies
    @Mock
    private lateinit var messageRepository: MessageRepository
    private val transformer = TestTransformer<Result<None>>()

    private fun getMessageIds() = listOf(1L, 2L, 3L)

    @Before
    fun setUp() {
        deleteMessagesUseCase = DeleteMessagesUseCase(messageRepository, transformer)
    }

    /**
     * Test if DeleteMessagesUseCase returns the exact result from its messageRepository
     * when deleting messages succeeded
     */
    @Test
    fun deleteMessagesSucceeded() {
        // GIVEN
        val input = DeleteMessagesUseCase.Input(getMessageIds())
        val result = Result.success(None())
        `when`(messageRepository.deleteMessages(input.messageIds))
                .thenReturn(Observable.just(result))

        // WHEN
        val testObserver = deleteMessagesUseCase.execute(input).test()

        // THEN
        testObserver
                .assertValue(result)
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Test if DeleteMessagesUseCase returns the exact result from its messageRepository
     * when deleting messages failed
     */
    @Test
    fun deleteMessagesFailed() {
        // GIVEN
        val input = DeleteMessagesUseCase.Input(getMessageIds())
        val result = Result.failure<None>("delete messages failed")
        `when`(messageRepository.deleteMessages(input.messageIds))
                .thenReturn(Observable.just(result))

        // WHEN
        val testObserver = deleteMessagesUseCase.execute(input).test()

        // THEN
        testObserver
                .assertValue(result)
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Test if DeleteMessagesUseCase converts error into a Failure
     * when deleting messages threw an error
     */
    @Test
    fun deleteMessagesError() {
        // GIVEN
        val input = DeleteMessagesUseCase.Input(getMessageIds())
        val throwable = Throwable("delete messages error")
        `when`(messageRepository.deleteMessages(input.messageIds))
                .thenReturn(Observable.error(throwable))

        // WHEN
        val testObserver = deleteMessagesUseCase.execute(input).test()

        // THEN
        testObserver
                .assertValue(Result.failure(throwable.message.orEmpty()))
                .assertNoErrors()
                .assertComplete()
    }
}