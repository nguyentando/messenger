package com.donguyen.messenger.ui.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.donguyen.messenger.R
import com.donguyen.messenger.util.extension.irisApplication
import javax.inject.Inject

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessagesActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MessagesVMFactory
    private lateinit var viewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        irisApplication.createMessagesComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(MessagesViewModel::class.java)

        // observe view state
        viewModel.viewState.observe(this,
                Observer { viewState -> handleViewState(viewState) })

        viewModel.loadMessages()
    }

    private fun handleViewState(state: MessagesViewState?) {
        state ?: return

        // TODO - handle view state
    }
}
