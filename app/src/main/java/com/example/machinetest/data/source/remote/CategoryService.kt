package com.example.machinetest.data.source.remote

import com.example.machinetest.data.source.remote.dto.CategoryResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CategoryService {

    @GET("v2/5ec39cba300000720039c1f6")
    suspend fun getCategories():ApiResponse<CategoryResponseDto>
}