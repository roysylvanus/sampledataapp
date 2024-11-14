package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: ProductEntity) =
        repository.updateProduct(product)
}