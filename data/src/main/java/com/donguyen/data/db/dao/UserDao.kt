package com.donguyen.data.db.dao

import androidx.room.Dao
import com.donguyen.data.model.UserData

/**
 * Created by DoNguyen on 23/10/18.
 */
@Dao
interface UserDao : BaseDao<UserData>