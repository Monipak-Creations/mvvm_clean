package com.example.machinetest.app.di

import android.app.Application
import androidx.room.Room
import com.example.machinetest.data.source.local.CategoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCategoryDatabase(application: Application) =
        Room.databaseBuilder(application, CategoryDatabase::class.java, "category_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCategoryDao(categoryDatabase: CategoryDatabase) = categoryDatabase.categoryDao

}