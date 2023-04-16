package com.example.machinetest.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories : List<CategoryEntity>)

    @Query("SELECT * FROM category_table ORDER BY title")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT COUNT(*) FROM category_table")
    fun getDatabaseItems(): Int

}