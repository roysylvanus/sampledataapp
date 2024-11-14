package com.techadive.sampledataapp.domain.use_cases

import com.techadive.sampledataapp.data.AppPreferences
import com.techadive.sampledataapp.data.JsonDataProvider
import com.techadive.sampledataapp.data.repositories.ProductRepository
import javax.inject.Inject

class LoadSampleDataUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val jsonDataProvider: JsonDataProvider,
    private val appPreferences: AppPreferences // Add dependency to check first launch
) {

    suspend operator fun invoke() {
        // Check if it's the first launch of the app
        if (appPreferences.isFirstLaunch()) {
            // Load the product data from JSON using the JsonDataProvider
            val products = jsonDataProvider.getProductsFromJson()

            // Insert the products into the Room database via the repository
            repository.insertAllProducts(products)

            // Mark the first launch as completed
            appPreferences.setFirstLaunchCompleted()
        }
    }
}