package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<ProductEntity>> =
        repository.getAllProducts()
}