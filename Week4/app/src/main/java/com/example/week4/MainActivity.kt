package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week4.ui.theme.Week4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week4Theme {

                //Tạo một NavController để quản lý việc chuyển màn hình
                val navController = rememberNavController()

                //NavHost
                NavHost(
                    navController = navController,
                    startDestination = "main_screen" // Màn hình bắt đầu
                ) {

                    composable(route = "main_screen") {
                        MainScreen(navController = navController)
                    }

                    composable(route = "library_management") {
                        LibraryManagementScreen(navController = navController)
                    }

                    composable(route = "navigation_oop") {
                        NavigationOOPScreen(navController = navController)
                    }

                    composable(route = "data_flow") {
                        DataFlowScreen(mainNavController = navController)
                    }
                }
            }
        }
    }
}