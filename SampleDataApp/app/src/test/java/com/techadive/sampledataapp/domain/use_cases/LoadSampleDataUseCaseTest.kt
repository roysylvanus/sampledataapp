package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.AppPreferences
import com.techadive.sampledataapp.data.JsonDataProvider
import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.data.repositories.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LoadSampleDataUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var jsonDataProvider: JsonDataProvider

    @Mock
    private lateinit var appPreferences: AppPreferences

    private lateinit var loadSampleDataUseCase: LoadSampleDataUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadSampleDataUseCase = LoadSampleDataUseCase(productRepository, jsonDataProvider, appPreferences)
    }

    @Test
    fun `invoke should load sample data and insert products when first launch`() = runBlocking {
        // Arrange
        val sampleProducts = listOf(
            ProductEntity(id = 1, name = "Product 1", description = "Description 1", price = 10.0, quantity = 5),
            ProductEntity(id = 2, name = "Product 2", description = "Description 2", price = 15.0, quantity = 3)
        )
        `when`(appPreferences.isFirstLaunch()).thenReturn(true)
        `when`(jsonDataProvider.getProductsFromJson()).thenReturn(sampleProducts)

        // Act
        loadSampleDataUseCase()

        // Assert
        verify(appPreferences, times(1)).isFirstLaunch()
        verify(jsonDataProvider, times(1)).getProductsFromJson()
        verify(productRepository, times(1)).insertAllProducts(sampleProducts)
        verify(appPreferences, times(1)).setFirstLaunchCompleted()
    }

    @Test
    fun `invoke should not load sample data when not first launch`() = runBlocking {
        // Arrange
        `when`(appPreferences.isFirstLaunch()).thenReturn(false)

        // Act
        loadSampleDataUseCase()

        // Assert
        verify(appPreferences, times(1)).isFirstLaunch()
        verify(jsonDataProvider, never()).getProductsFromJson()
        verify(productRepository, never()).insertAllProducts(anyList())
        verify(appPreferences, never()).setFirstLaunchCompleted()
    }
}
