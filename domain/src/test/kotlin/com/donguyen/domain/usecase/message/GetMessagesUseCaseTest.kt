package com.donguyen.domain.usecase.message

import androidx.paging.PagedList
import com.donguyen.domain.model.Message
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.test.TestTransformer
import com.donguyen.domain.test.createMockPagedList
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
class GetMessagesUseCaseTest {

    // sut
    private lateinit var getMessagesUseCase: GetMessagesUseCase

    // dependencies
    @Mock
    private lateinit var messageRepository: MessageRepository
    private val transformer = TestTransformer<Result<PagedList<Message>>>()

    @Before
    fun setUp() {
        getMessagesUseCase = GetMessagesUseCase(messageRepository, transformer)
    }

    /**
     * Test if GetMessagesUseCase returns the exact result from its messageRepository
     * when getting messages succeeded
     */
    @Test
    fun testGetMessagesSucceeded() {
        // GIVEN
        val result = Result.success(createMockPagedList<Message>())
        `when`(messageRepository.getMessages())
                .thenReturn(Observable.just(result))

        // WHEN
        val testObserver = getMessagesUseCase.execute(None()).test()

        // THEN
        testObserver
                .assertValue(result)
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Test if GetMessagesUseCase returns the exact result from its messageRepository
     * when getting messages failed
     */
    @Test
    fun testGetMessagesFailed() {
        // GIVEN
        val result = Result.failure<PagedList<Message>>("get messages failed")
        `when`(messageRepository.getMessages())
                .thenReturn(Observable.just(result))

        // WHEN
        val testObserver = getMessagesUseCase.execute(None()).test()

        // THEN
        testObserver
                .assertValue(result)
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Test if GetMessagesUseCase converts error into a Failure
     * when getting messages threw an error
     */
    @Test
    fun testGetMessagesError() {
        // GIVEN
        val throwable = Throwable("get messages error")
        `when`(messageRepository.getMessages())
                .thenReturn(Observable.error(throwable))

        // WHEN
        val testObserver = getMessagesUseCase.execute(None()).test()

        // THEN
        testObserver
                .assertValue(Result.failure(throwable.message.orEmpty()))
                .assertNoErrors()
                .assertComplete()
    }
}