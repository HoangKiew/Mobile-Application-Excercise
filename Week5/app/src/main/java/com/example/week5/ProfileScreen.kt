package com.example.week5.ui.screens // Em hãy thay bằng package của mình

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.R // R của project em

// Màu xanh chủ đạo
private val PrimaryBlue = Color(0xFF0084FF)

/**
 * Màn hình Profile đầy đủ
 * Sử dụng Scaffold để chia bố cục TopBar, Content, BottomBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        // --- 1. TOP BAR ---
        // Thanh tiêu đề "Profile" và nút Back
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Profile",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Nền trong suốt
                )
            )
        },

        // --- 2. BOTTOM BAR ---
        // Nút "Back" luôn nằm ở dưới đáy
        bottomBar = {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(26.dp) // Bo tròn
            ) {
                Text(
                    text = "Back",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },

        // --- 3. CONTENT ---
        // Nội dung chính của màn hình
        containerColor = Color.White // Nền trắng
    ) { innerPadding ->
        // innerPadding là khoảng đệm an toàn mà Scaffold cung cấp
        // để nội dung không bị TopBar và BottomBar che khuất

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Áp dụng padding
                .padding(horizontal = 24.dp), // Padding của riêng ta
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // --- Phần Avatar ---
            AvatarSection()

            Spacer(modifier = Modifier.height(32.dp))

            // --- Các trường thông tin ---
            InfoField(label = "Name", value = "Melissa Peters")

            Spacer(modifier = Modifier.height(16.dp))

            InfoField(label = "Email", value = "melpeters@gmail.com")

            Spacer(modifier = Modifier.height(16.dp))

            // Dùng Composable riêng cho Date vì có icon mũi tên
            DateField(label = "Date of Birth", value = "23/05/1995")
        }
    }
}

// --- Các Composable Con ---

/**
 * Phần Avatar (Ảnh đại diện và icon camera)
 */
@Composable
private fun AvatarSection() {
    Box(
        modifier = Modifier.size(140.dp)
    ) {
        // Ảnh đại diện
        Image(
            painter = painterResource(id = R.drawable.avatar), // Ảnh em vừa thêm
            contentDescription = "Profile Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
        )
        // Icon Camera
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .clickable { /* TODO: Xử lý đổi ảnh */ },
            shadowElevation = 4.dp
        ) {
            Icon(
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "Change Picture",
                tint = Color.Gray,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

/**
 * Trường thông tin (Dùng cho Name và Email)
 */
@Composable
private fun InfoField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Nhãn
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Ô text
        TextField(
            value = value,
            onValueChange = {}, // Không cho sửa
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5), // Nền xám nhạt
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5)
            ),
            singleLine = true
        )
    }
}

/**
 * Trường ngày sinh (Có mũi tên dropdown)
 */
@Composable
private fun DateField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Nhãn
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Hộp ngày sinh (Giả lập_
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight) // Cao bằng TextField
                .clickable { /* TODO: Mở Date Picker */ },
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF5F5F5) // Nền xám nhạt
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = value, color = Color.Black)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Select Date",
                    tint = Color.Gray
                )
            }
        }
    }
}


// --- Preview để xem nhanh ---
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}