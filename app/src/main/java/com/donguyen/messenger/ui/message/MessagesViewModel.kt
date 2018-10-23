package com.donguyen.messenger.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.donguyen.domain.model.Message
import com.donguyen.domain.usecase.Failure
import com.donguyen.domain.usecase.Success
import com.donguyen.domain.usecase.message.GetMessagesUseCase
import com.donguyen.domain.util.None
import com.donguyen.messenger.base.BaseViewModel

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessagesViewModel(private val getMessagesUseCase: GetMessagesUseCase) : BaseViewModel() {

    private val mViewState: MutableLiveData<MessagesViewState> = MutableLiveData()
    val viewState: LiveData<MessagesViewState> = mViewState

    init {
        mViewState.value = MessagesViewState()
    }

    fun loadMessages() {
        mViewState.value = viewState.value?.copy(loading = true)
        getMessagesUseCase.execute(None())
                .subscribe {
                    when (it) {
                        is Success -> handleGetMessagesSuccess(it.data)
                        is Failure -> handleFailure(it.error)
                    }
                }
                .autoClear()
    }

    private fun handleGetMessagesSuccess(messages: PagedList<Message>) {
        mViewState.value = viewState.value?.copy(
                loading = false,
                messages = messages,
                error = ""
        )
    }

    private fun handleFailure(error: String) {
        mViewState.value = viewState.value?.copy(
                loading = false,
                error = error
        )
    }
}