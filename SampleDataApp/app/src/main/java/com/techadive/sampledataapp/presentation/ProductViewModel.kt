package com.techadive.sampledataapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.sampledataapp.data.models.ProductEntity
import com.techadive.sampledataapp.domain.use_cases.GetProductByIdUseCase
import com.techadive.sampledataapp.domain.use_cases.GetProductsUseCase
import com.techadive.sampledataapp.domain.use_cases.LoadSampleDataUseCase
import com.techadive.sampledataapp.domain.use_cases.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val loadSampleDataUseCase: LoadSampleDataUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    // State to hold the list of products
    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> get() = _products

    // State to hold the loading status
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    // State to hold the selected product
    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct: StateFlow<ProductEntity?> get() = _selectedProduct

    init {
        loadSampleData()

        // Fetch products when ViewModel is initialized
        fetchProducts()
    }

    // Function to fetch products and update the loading state
    private fun fetchProducts() {
        viewModelScope.launch {
            _loading.value = true  // Set loading to true before starting the fetch
            getProductsUseCase().collect {
                _products.value = it  // Update the product list
                _loading.value = false  // Set loading to false after the fetch is complete
            }

            Log.i("products", _products.value.toString())
        }
    }

    // Function to update the product quantity and persist it to the database
    fun updateQuantity(product: ProductEntity, newQuantity: Int) {
        viewModelScope.launch {
            if (product.quantity != newQuantity) {
                val updatedProduct = product.copy(quantity = newQuantity)
                updateProductUseCase(updatedProduct)  // Update product in the repository
            }
        }
    }

    // Function to load sample data
    private fun loadSampleData() {
        viewModelScope.launch {
            loadSampleDataUseCase()  // Load the sample data
        }
    }

    // Function to fetch product by ID and update the selected product state
    fun getProductById(productId: Int) {
        viewModelScope.launch {
            _selectedProduct.value = getProductByIdUseCase(productId)
        }
    }
}