package com.example.machinetest.data.mapper

import com.example.machinetest.data.source.local.ProductEntity
import com.example.machinetest.data.source.remote.dto.ProductDto
import com.example.machinetest.domain.model.Product

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = System.currentTimeMillis(),
        description = description ?: "Not found",
        imageUrl = imageUrl ?: "",
        price = price ?: 0,
        title = title ?: "Not found"
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id, description, imageUrl, price, title
    )
}