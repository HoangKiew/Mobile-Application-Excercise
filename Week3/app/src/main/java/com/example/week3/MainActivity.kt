package com.example.week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week3Theme {
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("components") { ComponentsScreen(navController) }
        composable("text") { TextScreen(navController) }
        composable("images") { ImagesScreen(navController) }
        composable("TextField") { TextFieldScreen(navController) }
        composable("PasswordField") {PasswordFieldScreen(navController) }
        composable("Column") { ColumnScreen(navController) }
        composable("Row") { RowScreen(navController) }
    }
}
