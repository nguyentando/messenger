package com.donguyen.messenger.ui.message

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.donguyen.messenger.R
import com.donguyen.messenger.ui.message.selection.MessageItemDetailsLookup
import com.donguyen.messenger.ui.message.selection.MessageItemKeyProvider
import com.donguyen.messenger.util.extension.irisApplication
import com.donguyen.messenger.util.extension.show
import com.donguyen.messenger.util.extension.toast
import kotlinx.android.synthetic.main.activity_messages.*
import javax.inject.Inject

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessagesActivity : AppCompatActivity(), MessageViewHolder.OnAttachmentViewListener {

    @Inject
    lateinit var factory: MessagesVMFactory
    private lateinit var viewModel: MessagesViewModel

    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var selectionTracker: SelectionTracker<Long>
    private var actionMode: ActionMode? = null

    // for handle deleting an attachment
    private var selectedAttachmentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        irisApplication.createMessagesComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(MessagesViewModel::class.java)

        initViews()
        observeViewState()

        // prevent reloading when configuration changed
        if (savedInstanceState != null) {
            handleViewState(viewModel.viewState.value)
            selectionTracker.onRestoreInstanceState(savedInstanceState)

            val messages = viewModel.viewState.value?.messages
            if (messages == null) {
                // There are 2 possible cases here
                // 1. This is a recreation after the system has killed our process
                // 2. This is a recreation after configuration changed, and previously we have no message
                // No matter what case it is, we need to reload data
                viewModel.loadMessages()
            }

            selectedAttachmentId = savedInstanceState.getString(ATTACHMENT_ID)
            if (selectedAttachmentId != null) {
                showAttachmentMenu(selectedAttachmentId!!)
            }
        } else {
            viewModel.loadMessages()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker.onSaveInstanceState(outState)
        outState.putString(ATTACHMENT_ID, selectedAttachmentId)
    }

    private fun initViews() {
        messagesAdapter = MessagesAdapter(this).apply {
            setHasStableIds(true)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.apply {
            layoutManager = linearLayoutManager
            adapter = messagesAdapter
        }

        // init selection tracker
        selectionTracker = SelectionTracker.Builder<Long>(
                "message-selection-id",
                recycler_view,
                MessageItemKeyProvider(messagesAdapter),
                MessageItemDetailsLookup(recycler_view),
                StorageStrategy.createLongStorage()
        ).build().apply { addObserver(selectionObserver) }

        messagesAdapter.selectionTracker = selectionTracker
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this,
                Observer { viewState -> handleViewState(viewState) })
    }

    private fun handleViewState(state: MessagesViewState?) {
        state ?: return
        messagesAdapter.submitList(state.messages)

        // handle empty view
        val isEmpty = state.messages?.isEmpty() ?: false
        empty_txt.show(isEmpty)
        recycler_view.show(!isEmpty)

        // handle loading view
        progress_bar.show(state.loading)

        // handle error
        if (state.error.isNotEmpty()) this.toast(state.error)
    }

    // ---------------------------------------------------------------------------------------------
    // HANDLE DELETING MESSAGES
    // ------------------------------------------------------------------------------------------ //

    private val selectionObserver = object : SelectionTracker.SelectionObserver<Long>() {

        override fun onSelectionChanged() {
            super.onSelectionChanged()
            when {
                selectionTracker.hasSelection() && actionMode == null -> {
                    actionMode = startSupportActionMode(actionModeController)
                    actionMode?.title = selectionTracker.selection.size().toString()
                }

                !selectionTracker.hasSelection() && actionMode != null -> {
                    actionMode?.finish()
                    actionMode = null
                }

                else -> {
                    actionMode?.title = selectionTracker.selection.size().toString()
                }
            }
        }

        override fun onSelectionRestored() {
            super.onSelectionRestored()
            if (selectionTracker.hasSelection() && actionMode == null) {
                actionMode = startSupportActionMode(actionModeController)
                actionMode?.title = selectionTracker.selection.size().toString()
            }
        }
    }

    private val actionModeController = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return when (selectedAttachmentId) {
                null -> {
                    mode.menuInflater.inflate(R.menu.menu_contextual_action_message, menu)
                    true
                }
                else -> false
            }
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete_menu_item -> {
                    deleteMessages(selectionTracker.selection)
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            clearSelection()
        }
    }

    private fun clearSelection() {
        selectionTracker.clearSelection()
        actionMode = null
    }

    private fun deleteMessages(messageIds: Iterable<Long>) {
        viewModel.deleteMessages(messageIds)
    }

    // ---------------------------------------------------------------------------------------------
    // HANDLE DELETING AN ATTACHMENT
    // ------------------------------------------------------------------------------------------ //

    override fun onAttachmentLongClicked(attachmentId: String) {
        showAttachmentMenu(attachmentId)
    }

    private fun showAttachmentMenu(attachmentId: String) {
        this.selectedAttachmentId = attachmentId
        messagesAdapter.isEnableSelection = false
        clearSelection()

        val menuItems = arrayOf<CharSequence>(getString(R.string.delete_attachment))
        AlertDialog.Builder(this)
                .setItems(menuItems) { _, _ ->
                    viewModel.deleteAttachment(attachmentId)
                }.setOnDismissListener {
                    onAttachmentMenuDismissed()
                }
                .show()
    }

    private fun onAttachmentMenuDismissed() {
        selectedAttachmentId = null
        messagesAdapter.isEnableSelection = true
        clearSelection()
    }

    companion object {
        private const val ATTACHMENT_ID = "attachment_id"
    }
}
