package com.techadive.sampledataapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.techadive.sampledataapp.presentation.ProductViewModel
import com.techadive.sampledataapp.presentation.components.SampleDataApp
import com.techadive.sampledataapp.ui.theme.SampleDataAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleDataAppTheme {
                val navController = rememberNavController()
                SampleDataApp(navController, viewModel)
            }
        }
    }
}