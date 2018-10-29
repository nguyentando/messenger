package com.donguyen.data.repository.user

import com.donguyen.data.db.dao.UserDao
import com.donguyen.data.model.UserData
import com.donguyen.domain.model.User
import com.donguyen.domain.repository.UserRepository
import com.donguyen.domain.usecase.Result
import com.donguyen.domain.util.Mapper
import com.donguyen.domain.util.None
import io.reactivex.Observable

/**
 * Created by DoNguyen on 23/10/18.
 */
class UserRepositoryImpl(
        private val userDao: UserDao, // can create a UserDataSource interface when having multiple data sources
        private val userToUserDataMapper: Mapper<User, UserData>,
        private val userDataToUserMapper: Mapper<UserData, User>)
    : UserRepository {

    override fun insertUsers(users: List<User>): Observable<Result<None>> {
        return Observable.fromCallable {
            userDao.insertItems(userToUserDataMapper.mapFromList(users))
            Result.success(None())
        }
    }

    override fun getUsers(): Observable<Result<List<User>>> {
        return userDao.getUsers().map {
            Result.success(userDataToUserMapper.mapFromList(it))
        }
    }
}