package com.donguyen.domain.repository

import com.donguyen.domain.usecase.Result
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
interface AttachmentRepository {

    fun deleteAttachment(attachmentId: String): Observable<Result<Boolean>>
}