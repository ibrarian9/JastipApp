package com.app.jastipapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataJastip (

    val poto: String? = null,
    val nama: String? = null,
    val harga: String? = null,
    val ig: String? = null,
    val desc: String? = null

) : Parcelable