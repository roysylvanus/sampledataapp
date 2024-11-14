package com.techadive.sampledataapp.data


import com.techadive.sampledataapp.data.localdatabase.ProductDao
import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductRepositoryImplTest {

    // Mock for ProductDao
    @Mock
    private lateinit var mockProductDao: ProductDao

    // Instance of ProductRepositoryImpl to be tested
    private lateinit var productRepository: ProductRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productRepository = ProductRepositoryImpl(mockProductDao)
    }

    /**
     * Test for getAllProducts() function in ProductRepository.
     */
    @Test
    fun `getAllProducts should return flow of product list`() = runTest {
        // Sample data
        val sampleProducts = listOf(
            ProductEntity(id = 1, name = "Product 1", quantity = 10, price = 100.0, description = ""),
            ProductEntity(id = 2, name = "Product 2", quantity = 20, price = 200.0, description = "")
        )

        // Mock the behavior of productDao.getAllProducts()
        `when`(mockProductDao.getAllProducts()).thenReturn(flowOf(sampleProducts))

        // Collect the flow from repository
        val productsFlow: Flow<List<ProductEntity>> = productRepository.getAllProducts()
        val productsList = productsFlow.first()

        // Verify the result
        assertEquals(sampleProducts, productsList)

        // Verify that the getAllProducts() method was called once
        verify(mockProductDao, times(1)).getAllProducts()
    }

    /**
     * Test for updateProduct() function in ProductRepository.
     */
    @Test
    fun `updateProduct should call updateProduct in productDao`() = runBlocking {
        // Sample product
        val product = ProductEntity(id = 1, name = "Product 1", quantity = 10, price = 100.0, description = "")

        // Call the updateProduct() method
        productRepository.updateProduct(product)

        // Verify that productDao.updateProduct() was called with the correct product
        verify(mockProductDao, times(1)).updateProduct(product)
    }

    /**
     * Test for insertAllProducts() function in ProductRepository.
     */
    @Test
    fun `insertAllProducts should call insertAll in productDao`() = runBlocking {
        // Sample product list
        val products = listOf(
            ProductEntity(id = 1, name = "Product 1", quantity = 10, price = 100.0, description = ""),
            ProductEntity(id = 2, name = "Product 2", quantity = 20, price = 200.0, description = "")
        )

        // Call the insertAllProducts() method
        productRepository.insertAllProducts(products)

        // Verify that productDao.insertAll() was called with the correct list
        verify(mockProductDao, times(1)).insertAll(products)
    }

    /**
     * Test for getProductById() function in ProductRepository.
     */
    @Test
    fun `When getProductById should return product from productDao`(): Unit = runBlocking {
        // Sample product
        val productId = 1
        val expectedProduct = ProductEntity(id = 1, name = "Product 1", quantity = 10, price = 100.0, description = "")

        // Mock the behavior of productDao.getProductById()
        `when`(mockProductDao.getProductById(productId)).thenReturn(expectedProduct)

        // Call the getProductById() method
        val result = productRepository.getProductById(productId)

        // Verify the result
        assertEquals(expectedProduct, result)

        // Verify that productDao.getProductById() was called with the correct ID
        verify(mockProductDao, times(1)).getProductById(productId)
    }
}