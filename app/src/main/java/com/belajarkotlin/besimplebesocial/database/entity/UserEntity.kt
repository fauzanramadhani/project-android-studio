package com.belajarkotlin.besimplebesocial.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val uid:Int?=null,
    var email:String,
    var nik:Long?=null,
    var fullName:String?=null,
    var address:String?=null,
    var noTel:Long?=null
)
