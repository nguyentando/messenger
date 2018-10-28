package com.donguyen.messenger.di.module

import android.content.Context
import com.donguyen.data.db.AppDatabase
import com.donguyen.data.db.dao.AttachmentDao
import com.donguyen.data.db.dao.MessageDao
import com.donguyen.data.db.dao.UserDao
import com.donguyen.data.repository.attachment.AttachmentRepositoryImpl
import com.donguyen.data.repository.message.MessageRepositoryImpl
import com.donguyen.data.repository.message.MessageWithAttachmentsToMessageMapper
import com.donguyen.data.repository.user.UserDataToUserMapper
import com.donguyen.data.repository.user.UserRepositoryImpl
import com.donguyen.data.repository.user.UserToUserDataMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Module
class DataModule {

    /* ---------------------------------------------------------------------------------------------
    * DATABASE
    * ------------------------------------------------------------------------------------------- */
    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) = AppDatabase.getInstance(context)

    /* ---------------------------------------------------------------------------------------------
    * DAO
    * ------------------------------------------------------------------------------------------- */
    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase) = database.messageDao()

    @Provides
    @Singleton
    fun provideAttachmentDao(database: AppDatabase) = database.attachmentDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()

    /* ---------------------------------------------------------------------------------------------
    * REPOSITORY
    * ------------------------------------------------------------------------------------------- */
    @Provides
    @Singleton
    fun provideMessageRepository(
            messageDao: MessageDao,
            messageWithAttachmentsToMessageMapper: MessageWithAttachmentsToMessageMapper) =

            MessageRepositoryImpl(messageDao, messageWithAttachmentsToMessageMapper)

    @Provides
    @Singleton
    fun provideAttachmentRepository(attachmentDao: AttachmentDao) = AttachmentRepositoryImpl(attachmentDao)

    @Provides
    @Singleton
    fun provideUserRepository(
            userDao: UserDao,
            userToUserDataMapper: UserToUserDataMapper,
            userDataToUserMapper: UserDataToUserMapper) =

            UserRepositoryImpl(userDao, userToUserDataMapper, userDataToUserMapper)
}