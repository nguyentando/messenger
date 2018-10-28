package com.donguyen.domain.repository

import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
interface AttachmentRepository {

    fun deleteAttachment(attachmentId: String): Observable<Result<None>>
}