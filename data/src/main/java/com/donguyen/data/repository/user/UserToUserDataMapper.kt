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
class UserToUserDataMapper @Inject constructor() : Mapper<User, UserData>() {

    override fun mapFrom(from: User): UserData {
        return UserData(
                id = from.id,
                name = from.name,
                avatarUrl = from.avatarUrl
        )
    }
}