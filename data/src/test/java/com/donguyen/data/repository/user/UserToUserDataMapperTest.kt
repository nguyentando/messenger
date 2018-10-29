package com.donguyen.data.repository.user

import com.donguyen.data.test.TestFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by DoNguyen on 28/10/18.
 */
class UserToUserDataMapperTest {

    // sut
    private val mapper = UserToUserDataMapper()

    @Test
    fun returnExpectedValue() {
        // GIVEN
        val user = TestFactory.createUser(1)

        // WHEN
        val userData = mapper.mapFrom(user)

        // THEN
        assertEquals(userData.id, user.id)
        assertEquals(userData.name, user.name)
        assertEquals(userData.avatarUrl, user.avatarUrl)
    }
}