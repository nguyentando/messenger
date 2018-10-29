package com.donguyen.data.db.dao

import com.donguyen.data.test.TestFactory
import org.junit.Test

/**
 * Created by DoNguyen on 29/10/18.
 */
class UserDaoTest : BaseDaoTest() {

    // sut
    private lateinit var userDao: UserDao

    override fun setUp() {
        super.setUp()
        userDao = appDatabase.userDao()
    }

    @Test
    fun getUsersSucceeded() {
        // GIVEN
        val users = arrayListOf(TestFactory.createUserData(1))
        userDao.insertItems(users)

        // WHEN
        val testObserver = userDao.getUsers().test()

        // THEN
        testObserver
                .assertValue(users)
                .assertNoErrors()
    }
}