package com.example.week3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun RowScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Row Layout", // Tiêu đề màn hình
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp), // Padding cho toàn bộ nội dung
            verticalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa các hàng
        ) {
            repeat(4) {
                LayoutRow()
            }
        }
    }
}

@Composable
fun LayoutRow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)) // Bo tròn khung
            .background(Color(0xFFF0F4F7)) // Nền xám nhạt cho khung
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các hộp xanh
        ) {
            val itemModifier = Modifier.weight(1f)

            ItemBox(modifier = itemModifier, color = Color(0xFFD0E6FF))
            ItemBox(modifier = itemModifier, color = Color(0xFF64B5F6))
            ItemBox(modifier = itemModifier, color = Color(0xFFD0E6FF))
        }
    }
}

@Composable
fun ItemBox(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .height(80.dp)
            .background(color, shape = RoundedCornerShape(16.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun RowScreenPreview() {
    Week3Theme {
        RowScreen(navController = rememberNavController())
    }
}