package com.donguyen.data.repository.user

import com.donguyen.data.db.dao.UserDao
import com.donguyen.data.test.TestFactory
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.None
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by DoNguyen on 28/10/18.
 */
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    // sut
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    // dependencies
    @Mock
    private lateinit var userDao: UserDao
    private val userToUserDataMapper = UserToUserDataMapper()
    private val userDataToUserMapper = UserDataToUserMapper()

    @Before
    fun setUp() {
        userRepositoryImpl = UserRepositoryImpl(userDao, userToUserDataMapper, userDataToUserMapper)
    }

    /* ---------------------------------------------------------------------------------------------
    * INSERT USERS
    * ------------------------------------------------------------------------------------------- */

    @Test
    fun insertUsersSucceeded() {
        // GIVEN userDao return no error when inserting users
        val users = arrayListOf(TestFactory.createUser(1))

        // WHEN inserting users
        val testObserver = userRepositoryImpl.insertUsers(users).test()

        // THEN delegate the logic to userDao and return Result.success
        verify(userDao).insertItems(userToUserDataMapper.mapFromList(users))
        testObserver
                .assertValue(Result.success(None()))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun insertUsersError() {
        // GIVEN userDao return error when inserting users
        val users = arrayListOf(TestFactory.createUser(1))
        val usersData = userToUserDataMapper.mapFromList(users)
        val throwable = Throwable("insert users error")
        `when`(userDao.insertItems(usersData))
                .then { throw throwable }

        // WHEN inserting users
        val testObserver = userRepositoryImpl.insertUsers(users).test()

        // THEN delegate the logic to userDao and receive error
        verify(userDao).insertItems(usersData)
        testObserver
                .assertError(throwable)
                .assertNoValues()
                .assertNotComplete()
    }

    /* ---------------------------------------------------------------------------------------------
    * GET USERS
    * ------------------------------------------------------------------------------------------- */

    @Test
    fun getUsersSucceeded() {
        // GIVEN userDao return no error when getting users
        val usersData = arrayListOf(TestFactory.createUserData(1))
        `when`(userDao.getUsers())
                .thenReturn(Observable.just(usersData))

        // WHEN getting users
        val testObserver = userRepositoryImpl.getUsers().test()

        // THEN delegate the logic to userDao and return Result.success
        verify(userDao).getUsers()
        testObserver
                .assertValue(Result.success(userDataToUserMapper.mapFromList(usersData)))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getUsersError() {
        // GIVEN userDao return error when getting users
        val throwable = Throwable("get users error")
        `when`(userDao.getUsers())
                .thenReturn(Observable.error(throwable))

        // WHEN getting users
        val testObserver = userRepositoryImpl.getUsers().test()

        // THEN delegate the logic to userDao and receive error
        verify(userDao).getUsers()
        testObserver
                .assertError(throwable)
                .assertNoValues()
                .assertNotComplete()
    }
}