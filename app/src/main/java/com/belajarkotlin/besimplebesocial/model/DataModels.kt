package com.belajarkotlin.besimplebesocial.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
@Keep
data class DataModels(
    val name: String = "",
    val uid: String = "",
    val description: String= "",
    val timePost: String = ""
    //val imageResourceId: Int
    ) {
}