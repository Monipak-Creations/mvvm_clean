package com.example.machinetest.data.source.local

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductEntity(
    val id: Long,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val title: String
)