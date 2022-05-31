package com.belajarkotlin.besimplebesocial.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belajarkotlin.besimplebesocial.database.dao.UserDao
import com.belajarkotlin.besimplebesocial.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}