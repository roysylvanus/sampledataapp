package com.techadive.sampledataapp.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.techadive.sampledataapp.data.models.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface JsonDataProvider {
    suspend fun getProductsFromJson(): List<ProductEntity>
}

class JsonDataProviderImpl @Inject constructor(private val assetManager: AssetManager) :
    JsonDataProvider {

    override suspend fun getProductsFromJson(): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            val json = assetManager.open("sample_data.json").bufferedReader().use { it.readText() }
            Gson().fromJson(json, Array<ProductEntity>::class.java).toList()
        }
    }
}

