package com.example.machinetest.data.source.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "products")
    val productDtos: List<ProductDto>?,
    @Json(name = "title")
    val title: String?
)