package com.example.week3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Column Layout",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF007AFF)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF007AFF)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF0F4F7))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Hộp 1
                ItemBox(color = Color(0xFFC8E6C9))

                // Hộp 2
                ItemBox(color = Color(0xFF81C784))

                // Hộp 3
                ItemBox(color = Color(0xFFC8E6C9))
            }
        }
    }
}

// Composable tái sử dụng cho các hộp màu xanh
@Composable
fun ItemBox(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color, shape = RoundedCornerShape(16.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun ColumnScreenPreview() {
    Week3Theme {
        ColumnScreen(navController = rememberNavController())
    }
}