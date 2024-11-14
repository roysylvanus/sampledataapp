package com.techadive.sampledataapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.techadive.sampledataapp.data.models.ProductEntity

@Composable
fun ProductListScreen(
    navController: NavHostController,
    products: List<ProductEntity>,
    loading: Boolean,
    updateQuantity: (ProductEntity, Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") }
            )
        }
    ) { paddingValues ->
        // Show loading spinner while fetching products
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onProductClick = { selectedProduct ->
                            // Navigate to the product detail screen
                            navController.navigate("product_detail/${selectedProduct.id}")
                        },
                        onQuantityChange = { newQuantity ->
                            updateQuantity(product, newQuantity)
                        }
                    )

                    Divider(modifier = Modifier
                        .padding(16.dp)
                        .height(1.dp), color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: ProductEntity,
    onProductClick: (ProductEntity) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(product.quantity) }

    Column(modifier = Modifier
        .padding(16.dp)
        .clickable {
            onProductClick(product)
        }) {
        Text(text = product.name, fontWeight = FontWeight.Bold)
        Text(text = product.description)
        Text(text = "Price: \$${product.price}")

        // Quantity input field
        OutlinedTextField(
            value = quantity.toString(),
            onValueChange = { newValue ->
                newValue.toIntOrNull()?.let { newQuantity ->
                    quantity = newQuantity
                    onQuantityChange(newQuantity)
                }
            },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}