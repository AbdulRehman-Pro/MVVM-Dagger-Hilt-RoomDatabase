package com.rehman.room.di

import android.content.Context
import androidx.room.Room
import com.rehman.room.dao.UserDao
import com.rehman.room.database.UserDatabase
import com.rehman.room.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideUserDao(userDatabase: UserDatabase) : UserDao = userDatabase.userDao()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : UserDatabase {
        return Room.databaseBuilder(context,UserDatabase::class.java,"user_db")
            .build()
    }

    @Provides
    fun provideUserRepo(userDao: UserDao) : UserRepository = UserRepository(userDao)

}