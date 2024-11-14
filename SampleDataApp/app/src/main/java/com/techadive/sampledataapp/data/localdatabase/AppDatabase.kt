package com.techadive.sampledataapp.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techadive.sampledataapp.data.models.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}