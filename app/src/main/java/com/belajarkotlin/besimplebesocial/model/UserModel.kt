package com.belajarkotlin.besimplebesocial.model

import com.belajarkotlin.besimplebesocial.database.entity.UserEntity

data class UserModel(
    val email: String,
    val nik: Long,
    val name: String,
    val noHouse: String,
    val noTelp: Long,
    val gajiFamily: Long,
    val hasData: Boolean)


fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        email = this.email,
        nik = this.nik,
        address = this.noHouse,
        fullName = this.name,
        noTel = this.noTelp,
    )
}


