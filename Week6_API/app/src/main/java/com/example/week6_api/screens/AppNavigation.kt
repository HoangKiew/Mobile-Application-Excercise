package com.example.week6_api.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
// Trong file AppNavigation.kt    package com.example.week6_api.screens

// ... các import khác ...
import com.example.week6_api.data.TaskViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val taskViewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        // Route 1: Màn hình chính
        composable("home") {
            HomeScreen(navController = navController, viewModel = taskViewModel)
        }

        // Route 2: Màn hình chi tiết
        composable(
            route = "detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            if (taskId != null) {
                DetailScreen(
                    navController = navController,
                    viewModel = taskViewModel,
                    taskId = taskId
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}
