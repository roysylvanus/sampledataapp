package com.techadive.sampledataapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    var quantity: Int
)