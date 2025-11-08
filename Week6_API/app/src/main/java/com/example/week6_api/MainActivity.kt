package com.example.week6_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.week6_api.screens.AppNavigation
import com.example.week6_api.ui.theme.Week6_apiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Dùng theme bạn đã định nghĩa trong ui.theme
            Week6_apiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Gọi hàm điều hướng chính của app
                    AppNavigation()
                }
            }
        }
    }
}
