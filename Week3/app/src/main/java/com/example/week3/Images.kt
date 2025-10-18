package com.example.week3

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesScreen(navController: NavController) { // 1. Thêm NavController
    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Images",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF007AFF)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF007AFF)
                        )
                    }
                }
            )
        }
    ) { innerPadding -> // Scaffold cung cấp padding
        val imageId1 = R.drawable.image
        val imageId2 = R.drawable.uth_bg_05

        val linkUrl = "https://giaothongvantaiphcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png"
        val uriHandler = LocalUriHandler.current

        Column(
            modifier = Modifier
                .padding(innerPadding) //Áp dụng padding
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Hình ảnh thứ nhất
            Image(
                painter = painterResource(id = imageId1),
                contentDescription = "Ảnh trường đại học",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            // --- Link  ---
            Text(
                text = linkUrl,
                modifier = Modifier.clickable {
                    uriHandler.openUri(linkUrl)
                },
                style = TextStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                textAlign = TextAlign.Center
            )

            // --- Hình ảnh thứ hai ---
            Image(
                painter = painterResource(id = imageId2),
                contentDescription = "Ảnh trường đại học",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Text(text = "In app")
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ImagesPreview() {
    Week3Theme {
        val navController = rememberNavController()
        ImagesScreen(navController = navController)
    }
}