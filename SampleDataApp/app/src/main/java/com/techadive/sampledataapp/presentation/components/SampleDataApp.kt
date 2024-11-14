package com.techadive.sampledataapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techadive.sampledataapp.presentation.ProductViewModel

@Composable
fun SampleDataApp(
    navController: NavHostController,
    viewModel: ProductViewModel
) {
    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") {
            ProductListScreen(
                navController = navController,
                products = viewModel.products.collectAsState().value,
                loading = viewModel.loading.collectAsState().value,
                updateQuantity = { product, newquantity ->
                    viewModel.updateQuantity(product, newquantity)
                }
            )
        }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailScreen(
                navController = navController,
                productId = productId,
                selectedProduct = viewModel.selectedProduct.collectAsState().value,
                getProductById = { id ->
                    viewModel.getProductById(id)
                }
            )
        }
    }
}