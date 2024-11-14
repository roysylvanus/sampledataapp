package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class UpdateProductUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var updateProductUseCase: UpdateProductUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updateProductUseCase = UpdateProductUseCase(productRepository)
    }

    @Test
    fun `invoke should call updateProduct on repository`(): Unit = runBlocking {
        // Arrange
        val product = ProductEntity(id = 1, name = "Product 1", description = "Description 1", price = 10.0, quantity = 5)

        // Act
        updateProductUseCase(product)

        // Assert
        verify(productRepository, times(1)).updateProduct(product)
    }
}
