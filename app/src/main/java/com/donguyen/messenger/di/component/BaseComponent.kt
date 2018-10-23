package com.donguyen.messenger.di.component

import com.donguyen.messenger.di.module.AppModule
import com.donguyen.messenger.di.module.DataModule
import com.donguyen.messenger.di.module.MessagesModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Singleton
@Component(
        modules = [
            AppModule::class,
            DataModule::class
        ]
)
interface BaseComponent {

    fun plus(messagesModule: MessagesModule): MessagesSubComponent
}