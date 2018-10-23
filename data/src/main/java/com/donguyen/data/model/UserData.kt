package com.donguyen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by DoNguyen on 23/10/18.
 */
@Entity(tableName = "users")
data class UserData(

        @PrimaryKey
        val id: Long,

        val name: String,

        @ColumnInfo(name = "avatar_url")
        @SerializedName("avatarId")
        val avatarUrl: String
)