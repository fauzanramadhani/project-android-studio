package com.belajarkotlin.besimplebesocial.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DataModels(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
    ) {
}