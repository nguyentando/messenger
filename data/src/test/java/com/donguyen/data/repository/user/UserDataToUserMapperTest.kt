package com.donguyen.data.repository.user

import com.donguyen.data.util.TestFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by DoNguyen on 28/10/18.
 */
class UserDataToUserMapperTest {

    // sut
    private val mapper = UserDataToUserMapper()

    @Test
    fun returnExpectedValue() {
        // GIVEN
        val userData = TestFactory.createUserData(1)

        // WHEN
        val user = mapper.mapFrom(userData)

        // THEN
        assertEquals(user.id, userData.id)
        assertEquals(user.name, userData.name)
        assertEquals(user.avatarUrl, userData.avatarUrl)
    }
}