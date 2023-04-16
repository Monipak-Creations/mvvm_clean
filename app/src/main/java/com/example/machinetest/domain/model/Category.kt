package com.example.machinetest.domain.model

data class Category(
    val id : String,
    val products: List<Product>,
    val title: String?
)
