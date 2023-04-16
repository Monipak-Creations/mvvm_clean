package com.example.machinetest.domain.repository

import com.example.machinetest.app.util.Resource
import com.example.machinetest.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<Resource<List<Category>>>
}