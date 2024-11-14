package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @Test
    fun `invoke should return list of products`() = runTest {
        // Arrange
        val expectedProducts = listOf(
            ProductEntity(id = 1, name = "Product 1", description = "Description 1", price = 10.0, quantity = 5),
            ProductEntity(id = 2, name = "Product 2", description = "Description 2", price = 15.0, quantity = 3)
        )
        // Mock the repository response
        `when`(productRepository.getAllProducts()).thenReturn(flowOf(expectedProducts))

        // Act
        val result: Flow<List<ProductEntity>> = getProductsUseCase()

        // Assert
        result.collect { products ->
            assertEquals(expectedProducts, products)
        }
        verify(productRepository, times(1)).getAllProducts()
    }

    @Test
    fun `invoke should return empty list when no products are available`() = runTest {
        // Arrange
        val expectedProducts = emptyList<ProductEntity>()
        `when`(productRepository.getAllProducts()).thenReturn(flowOf(expectedProducts))

        // Act
        val result: Flow<List<ProductEntity>> = getProductsUseCase()

        // Assert
        result.collect { products ->
            assertEquals(expectedProducts, products)
        }
        verify(productRepository, times(1)).getAllProducts()
    }
}