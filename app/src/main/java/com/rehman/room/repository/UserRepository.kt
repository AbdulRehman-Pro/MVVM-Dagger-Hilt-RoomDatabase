package com.rehman.room.repository

import com.rehman.room.dao.UserDao
import com.rehman.room.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    val getUserData: Flow<List<UserModel>> = userDao.fetchUser()

    suspend fun insert(userModel: UserModel) = withContext(Dispatchers.IO){
      userDao.insertUser(userModel)
    }

    suspend fun deleteUser(id: Int) = withContext(Dispatchers.IO){
        userDao.deleteUserById(id)
    }


}