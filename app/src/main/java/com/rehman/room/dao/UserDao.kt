package com.rehman.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rehman.room.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userModel: UserModel)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun fetchUser() : Flow<List<UserModel>>

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)
}