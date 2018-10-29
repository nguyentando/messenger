package com.donguyen.data.repository.attachment

import com.donguyen.data.db.dao.AttachmentDao
import com.donguyen.domain.repository.AttachmentRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class AttachmentRepositoryImpl(
        // can create an AttachmentDataSource interface when having multiple data sources
        private val attachmentDao: AttachmentDao)
    : AttachmentRepository {

    override fun deleteAttachment(attachmentId: String): Observable<Result<None>> {
        return Observable.fromCallable {
            attachmentDao.deleteById(attachmentId)
            Result.success(None())
        }
    }
}