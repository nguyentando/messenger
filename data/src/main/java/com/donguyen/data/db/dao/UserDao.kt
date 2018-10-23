package com.donguyen.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.donguyen.data.model.UserData
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
@Dao
interface UserDao : BaseDao<UserData> {

    @Query("SELECT * FROM users")
    fun getUsers(): Observable<List<UserData>>
}