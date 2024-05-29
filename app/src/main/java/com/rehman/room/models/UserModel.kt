package com.rehman.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
)
