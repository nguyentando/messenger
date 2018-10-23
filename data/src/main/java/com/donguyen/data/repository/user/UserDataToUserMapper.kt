package com.donguyen.data.repository.user

import com.donguyen.data.model.UserData
import com.donguyen.domain.model.User
import com.donguyen.domain.util.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by DoNguyen on 23/10/18.
 */
@Singleton
class UserDataToUserMapper @Inject constructor() : Mapper<UserData, User>() {

    override fun mapFrom(from: UserData): User {
        return User(
                id = from.id,
                name = from.name,
                avatarUrl = from.avatarUrl
        )
    }
}