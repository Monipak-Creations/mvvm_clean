package com.example.machinetest.data.source.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "description")
    val description: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "price")
    val price: Int?,
    @Json(name = "title")
    val title: String?
)