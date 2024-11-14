package com.techadive.sampledataapp.data.repositories

import com.techadive.sampledataapp.data.localdatabase.ProductDao
import com.techadive.sampledataapp.data.models.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepository {
    fun getAllProducts(): Flow<List<ProductEntity>>
    suspend fun updateProduct(product: ProductEntity)
    suspend fun insertAllProducts(products: List<ProductEntity>)
    suspend fun getProductById(productId: Int): ProductEntity?
}

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
) : ProductRepository {

    /**
     * Fetches all products from the Room database as a Flow.
     */
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    /**
     * Updates a single product in the Room database.
     */
    override suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    /**
     * Inserts a list of products into the Room database.
     * If a product already exists, it will be replaced.
     */
    override suspend fun insertAllProducts(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    /**
     * Fetches a single product by its ID from the Room database.
     */
    override suspend fun getProductById(productId: Int): ProductEntity? {
        return productDao.getProductById(productId)
    }
}