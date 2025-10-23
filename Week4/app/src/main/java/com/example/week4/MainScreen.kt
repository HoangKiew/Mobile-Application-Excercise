package com.example.week4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


val myPinkColor = Color(0xFF2196f3)
val myLightPinkBackground = Color(0xFFFFFFFF)

@Composable
fun MainScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = myLightPinkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BTVN TUẦN 4",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(40.dp))

            PracticeButton(
                text = "Thực hành 1",
                onClick = { navController.navigate("library_management") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            PracticeButton(
                text = "Thực hành 2",
                onClick = { navController.navigate("navigation_oop") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            PracticeButton(
                text = "Thực hành 3",
                onClick = { navController.navigate("data_flow") }
            )
        }
    }
}

@Composable
fun PracticeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = myPinkColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.White)
    }
}
@Preview(showBackground = true, name = "Màn hình chính")
@Composable
fun MainScreenPreview() {

    val navController = rememberNavController()

    MainScreen(navController = navController)
}