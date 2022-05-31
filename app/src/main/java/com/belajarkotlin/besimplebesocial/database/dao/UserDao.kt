package com.belajarkotlin.besimplebesocial.database.dao

import androidx.room.*
import com.belajarkotlin.besimplebesocial.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM tb_user WHERE uid=:uid")
    fun getDataUser(uid: Long): UserEntity?

    @Query("SELECT * FROM tb_user WHERE email=:email")
    fun getDataUserByEmail(email: String): UserEntity?

    @Insert
    fun insertUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)

    @Query("SELECT * FROM tb_user WHERE email=:email")
    fun checkUserHasData(email: String): Boolean {
        val user = getDataUserByEmail(email)
        if (user?.nik != null) {
            return true
        }
        return false
    }
}