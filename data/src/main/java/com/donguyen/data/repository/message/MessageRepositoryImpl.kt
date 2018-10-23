package com.donguyen.data.repository.message

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.donguyen.data.db.AppDatabase
import com.donguyen.data.db.dao.MessageDao
import com.donguyen.data.model.MessageData
import com.donguyen.data.model.MessageWithAttachments
import com.donguyen.domain.model.Message
import com.donguyen.domain.repository.MessageRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.Mapper
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class MessageRepositoryImpl(
        // can create a MessageDataSource interface when having multiple data sources
        private val messageDao: MessageDao,
        private val messageWithAttachmentsToMessageMapper: Mapper<MessageWithAttachments, Message>,
        private val messageToMessageDataMapper: Mapper<Message, MessageData>
) : MessageRepository {

    override fun getMessages(): Observable<Result<PagedList<Message>>> {
        val dataSourceFactory = messageDao.getAllMessagesWithAttachments()
                .map(messageWithAttachmentsToMessageMapper::mapFrom)

        val pagingConfig = PagedList.Config.Builder()
                .setPageSize(AppDatabase.PAGE_SIZE)
                .setPrefetchDistance(AppDatabase.PREFETCH_DISTANCE)
                .setInitialLoadSizeHint(AppDatabase.INITIAL_LOAD_SIZE)
                .setEnablePlaceholders(false)
                .build()

        return RxPagedListBuilder(dataSourceFactory, pagingConfig)
                .buildObservable()
                .map {
                    Result.success(it)
                }
    }

    override fun deleteMessages(messages: List<Message>): Observable<Result<Boolean>> {
        return Observable.fromCallable {
            messageDao.deleteItems(messageToMessageDataMapper.mapFromList(messages))
            Result.success(true)
        }
    }
}