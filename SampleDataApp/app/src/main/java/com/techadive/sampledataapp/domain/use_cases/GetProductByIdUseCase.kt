package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): ProductEntity? {
        return productRepository.getProductById(productId)
    }
}
