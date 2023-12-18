package com.app.jastipapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails (

    val userEmail: String? = null,
    val userName: String? = null

) : Parcelable
