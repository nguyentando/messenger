package com.donguyen.messenger.di.module

import android.content.Context
import com.donguyen.data.db.AppDatabase
import com.donguyen.data.db.dao.AttachmentDao
import com.donguyen.data.db.dao.MessageDao
import com.donguyen.data.db.dao.UserDao
import com.donguyen.data.repository.attachment.AttachmentRepositoryImpl
import com.donguyen.data.repository.message.MessageRepositoryImpl
import com.donguyen.data.repository.message.MessageToMessageDataMapper
import com.donguyen.data.repository.message.MessageWithAttachmentsToMessageMapper
import com.donguyen.data.repository.user.UserDataToUserMapper
import com.donguyen.data.repository.user.UserRepositoryImpl
import com.donguyen.data.repository.user.UserToUserDataMapper
import com.donguyen.domain.repository.AttachmentRepository
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.repository.UserRepository
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
    fun provideRoomDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    /* ---------------------------------------------------------------------------------------------
    * DAO
    * ------------------------------------------------------------------------------------------- */
    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    @Singleton
    fun provideAttachmentDao(database: AppDatabase): AttachmentDao {
        return database.attachmentDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    /* ---------------------------------------------------------------------------------------------
    * REPOSITORY
    * ------------------------------------------------------------------------------------------- */
    @Provides
    @Singleton
    fun provideMessageRepository(
            messageDao: MessageDao,
            messageWithAttachmentsToMessageMapper: MessageWithAttachmentsToMessageMapper,
            messageToMessageDataMapper: MessageToMessageDataMapper
    ): MessageRepository {

        return MessageRepositoryImpl(
                messageDao,
                messageWithAttachmentsToMessageMapper,
                messageToMessageDataMapper)
    }

    @Provides
    @Singleton
    fun provideAttachmentRepository(attachmentDao: AttachmentDao): AttachmentRepository {
        return AttachmentRepositoryImpl(attachmentDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
            userDao: UserDao,
            userToUserDataMapper: UserToUserDataMapper,
            userDataToUserMapper: UserDataToUserMapper
    ): UserRepository {

        return UserRepositoryImpl(userDao, userToUserDataMapper, userDataToUserMapper)
    }
}