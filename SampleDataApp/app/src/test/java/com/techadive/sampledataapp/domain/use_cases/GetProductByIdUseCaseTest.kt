package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetProductByIdUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductByIdUseCase: GetProductByIdUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductByIdUseCase = GetProductByIdUseCase(productRepository)
    }

    @Test
    fun `invoke should return product when product exists`(): Unit = runBlocking {
        // Arrange
        val productId = 1
        val expectedProduct = ProductEntity(id = productId, name = "Sample Product", quantity = 5, description = "m", price = 5.0)
        `when`(productRepository.getProductById(productId)).thenReturn(expectedProduct)

        // Act
        val result = getProductByIdUseCase(productId)

        // Assert
        assertThat(
            expectedProduct,
            equalTo(result)
        )
        verify(productRepository, times(1)).getProductById(productId)
    }

    @Test
    fun `invoke should return null when product does not exist`(): Unit = runBlocking {
        // Arrange
        val productId = 2
        `when`(productRepository.getProductById(productId)).thenReturn(null)

        // Act
        val result = getProductByIdUseCase(productId)

        // Assert
        assertThat(result, equalTo(null))
        verify(productRepository, times(1)).getProductById(productId)
    }
}
