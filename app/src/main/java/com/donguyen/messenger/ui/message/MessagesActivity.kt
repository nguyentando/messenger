package com.donguyen.messenger.ui.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.donguyen.messenger.R
import com.donguyen.messenger.util.extension.irisApplication
import com.donguyen.messenger.util.extension.toast
import kotlinx.android.synthetic.main.activity_messages.*
import javax.inject.Inject

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessagesActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MessagesVMFactory
    private lateinit var viewModel: MessagesViewModel

    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        irisApplication.createMessagesComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(MessagesViewModel::class.java)

        initViews()
        observeViewState()

        viewModel.loadMessages()
    }

    private fun initViews() {
        messagesAdapter = MessagesAdapter().apply {
            setHasStableIds(true)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.apply {
            layoutManager = linearLayoutManager
            adapter = messagesAdapter
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this,
                Observer { viewState -> handleViewState(viewState) })
    }

    private fun handleViewState(state: MessagesViewState?) {
        state ?: return
        messagesAdapter.submitList(state.messages)
        if (state.error.isNotEmpty()) this.toast(state.error)
        // TODO - handle loading
    }
}
