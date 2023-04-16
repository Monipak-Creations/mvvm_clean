package com.example.machinetest.data.repository

import com.example.machinetest.app.util.Resource
import com.example.machinetest.data.mapper.toCategory
import com.example.machinetest.data.mapper.toCategoryEntity
import com.example.machinetest.data.source.local.CategoryDao
import com.example.machinetest.data.source.remote.CategoryService
import com.example.machinetest.data.source.remote.dto.CategoryResponseDto
import com.example.machinetest.domain.model.Category
import com.example.machinetest.domain.repository.CategoryRepository
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val categoryService: CategoryService
) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        val data = fetchFromDatabase().first()
        val shouldFetch = categoryDao.getDatabaseItems() == 0
        if (shouldFetch) {
            categoryService.getCategories().suspendOnSuccess {
                saveCategories(this.data)
                emit(Resource.Success(fetchFromDatabase().first()))
            }.suspendOnError {
                when (statusCode) {
                    StatusCode.InternalServerError -> emit(Resource.Error(SERVER_ERROR))
                    else -> emit(Resource.Error(INTERNET_ERROR))
                }
            }.suspendOnException {
                emit(Resource.Error(this.message ?: UNKNOWN_ERROR))
            }
        } else {
            emit(Resource.Success(data))
        }
    }.flowOn(Dispatchers.IO)

    private fun fetchFromDatabase(): Flow<List<Category>> {
        return categoryDao.getCategories()
            .map { it.map { categoryEntity -> categoryEntity.toCategory() } }
    }

    private suspend fun saveCategories(categoryResponse: CategoryResponseDto) {
        val categoryEntities =
            categoryResponse.categoryDtos?.map { it.toCategoryEntity() } ?: emptyList()
        categoryDao.insertCategories(categoryEntities)

    }

    companion object{
        const val SERVER_ERROR = "Server error"
        const val UNKNOWN_ERROR = "Unknown error"
        const val INTERNET_ERROR = "Error. Please check your internet connection and try again"

    }
}