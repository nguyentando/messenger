package com.donguyen.messenger.di.component

import com.donguyen.messenger.di.module.MessagesModule
import com.donguyen.messenger.di.scope.PerActivity
import com.donguyen.messenger.ui.message.MessagesActivity
import dagger.Subcomponent

/**
 * Created by DoNguyen on 23/10/18.
 */
@PerActivity
@Subcomponent(modules = [MessagesModule::class])
interface MessagesSubComponent {

    fun inject(messagesActivity: MessagesActivity)
}