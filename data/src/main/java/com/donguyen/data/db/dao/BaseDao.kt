package com.donguyen.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Created by DoNguyen on 23/10/18.
 */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<T>)

    @Delete
    fun deleteItem(item: T)

    @Delete
    fun deleteItems(items: List<T>)
}