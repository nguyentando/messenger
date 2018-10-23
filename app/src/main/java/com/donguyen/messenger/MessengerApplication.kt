package com.donguyen.messenger

import android.app.Application
import com.donguyen.messenger.di.component.BaseComponent
import com.donguyen.messenger.di.component.DaggerBaseComponent
import com.donguyen.messenger.di.component.MessagesSubComponent
import com.donguyen.messenger.di.module.AppModule
import com.donguyen.messenger.di.module.DataModule
import com.donguyen.messenger.di.module.MessagesModule

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessengerApplication : Application() {

    private lateinit var baseComponent: BaseComponent

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        baseComponent = DaggerBaseComponent.builder()
                .appModule(AppModule(applicationContext))
                .dataModule(DataModule())
                .build()
    }

    fun getBaseComponent(): BaseComponent {
        return baseComponent
    }

    fun createMessagesComponent(): MessagesSubComponent {
        return baseComponent.plus(MessagesModule())
    }
}