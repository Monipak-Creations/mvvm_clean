package com.example.machinetest.data.mapper

import com.example.machinetest.data.source.local.CategoryEntity
import com.example.machinetest.data.source.remote.dto.CategoryDto
import com.example.machinetest.domain.model.Category

fun CategoryDto.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        productsEntity = productDtos?.map { it.toProductEntity() } ?: emptyList(),
        title = title ?: "Not found"
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = title,
        products = productsEntity.map { it.toProduct() },
        title = title
    )
}