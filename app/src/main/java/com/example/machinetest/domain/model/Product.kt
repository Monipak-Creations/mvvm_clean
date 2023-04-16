package com.example.machinetest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val title: String
) : Parcelable