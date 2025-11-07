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
import coil.compose.AsyncImage
import com.example.week5.R // R của project em
import com.google.firebase.auth.FirebaseUser

// Màu xanh chủ đạo
private val PrimaryBlue = Color(0xFF0084FF)

/**
 * Màn hình Profile đầy đủ
 * Sử dụng Scaffold để chia bố cục TopBar, Content, BottomBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: FirebaseUser?,
    onSignOutClick: () -> Unit = {}
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
                // Không cần nút back ở đây nữa vì có nút Sign Out
                navigationIcon = { },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Nền trong suốt
                )
            )
        },

        // --- 2. BOTTOM BAR ---
        // *** SỬA: Đổi thành nút "Sign Out" ***
        bottomBar = {
            Button(
                onClick = onSignOutClick, // Gọi hành động đăng xuất
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
                    text = "Sign Out", // Đổi chữ
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Áp dụng padding
                .padding(horizontal = 24.dp), // Padding của riêng ta
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // --- Phần Avatar ---
            // *** SỬA: Hiển thị ảnh từ URL của user ***
            AvatarSection(photoUrl = user?.photoUrl?.toString())

            Spacer(modifier = Modifier.height(32.dp))

            // --- Các trường thông tin ---
            // *** SỬA: Hiển thị tên và email thật ***
            InfoField(label = "Name", value = user?.displayName ?: "N/A")

            Spacer(modifier = Modifier.height(16.dp))

            InfoField(label = "Email", value = user?.email ?: "N/A")

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
private fun AvatarSection(photoUrl: String?) {
    Box(
        modifier = Modifier.size(140.dp)
    ) {
        // *** SỬA: Dùng AsyncImage của Coil để tải ảnh từ URL ***
        AsyncImage(
            model = photoUrl,
            contentDescription = "Profile Avatar",
            placeholder = painterResource(id = R.drawable.avatar), // Ảnh mặc định
            error = painterResource(id = R.drawable.avatar),      // Ảnh khi lỗi
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
        )

        // Icon Camera (giữ nguyên)
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
    // Preview sẽ không hiển thị user thật, nên ta để null
    ProfileScreen(user = null)
}
