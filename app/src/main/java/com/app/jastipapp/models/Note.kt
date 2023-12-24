package com.app.jastipapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val nama: String? = null,
    val barang: String? = null,
    val harga: String? = null,
    val jumlah: String? = null,
    val jastip: String? = null,
    val foto: String? = null,
) : Parcelable