package com.example.machinetest.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category_table")
data class CategoryEntity(
    val productsEntity : List<ProductEntity>,
    @PrimaryKey
    val title:String
)
