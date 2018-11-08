package com.donguyen.domain.repository

import com.donguyen.domain.model.User
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
interface UserRepository {

    /**
     * Insert users.
     */
    fun insertUsers(users: List<User>): Observable<Result<None>>

    /**
     * Get all users in the database.
     */
    fun getUsers(): Observable<Result<List<User>>>
}