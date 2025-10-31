package com.example.week5.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.R // Đây là R của project, ví dụ: R.drawable.uth_logo

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit = {} // Hàm này sẽ được gọi khi nhấn nút
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // --- 1. Ảnh Nền ---
        // Vẫn giữ nguyên, nó sẽ fill toàn bộ màn hình
        Image(
            painter = painterResource(id = R.drawable.background_uth), // Tên file của em
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f
        )

        // --- 2. Nội Dung Chính (ĐÃ SỬA ĐỔI) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Chỉ padding ngang, dọc chúng ta sẽ tự kiểm soát
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            // *** BỎ `verticalArrangement = Arrangement.SpaceBetween` ***
        ) {

            // --- Đây là các khoảng cách em có thể tự chỉnh ---

            // 1. Khoảng đệm từ đỉnh màn hình
            Spacer(modifier = Modifier.height(80.dp)) // Em có thể đổi 80.dp thành số khác

            // 2. Phần Logo UTH
            LogoSection()

            // 3. Khoảng cách giữa Logo và Tiêu đề
            Spacer(modifier = Modifier.height(24.dp))
            TitleSection()

            // 4. Khoảng cách giữa Tiêu đề và Chào mừng
            Spacer(modifier = Modifier.height(48.dp))
            WelcomeSection()

            // 5. Khoảng cách giữa Chào mừng và Nút
            Spacer(modifier = Modifier.height(32.dp))
            SignInButton(onClick = onSignInClick)

            // 6. PHẦN QUAN TRỌNG NHẤT:
            // Spacer này sẽ chiếm "hết" tất cả không gian còn lại
            // và "đẩy" mọi thứ bên dưới nó xuống đáy màn hình.
            Spacer(modifier = Modifier.weight(1f))

            // 7. Phần Footer
            FooterSection()

            // 8. Đệm 1 chút ở dưới cùng
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- Các Composable Con ---

@Composable
private fun LogoSection() {
    // Dùng Surface để tạo nền bo tròn màu xanh nhạt
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFE0F7FA).copy(alpha = 0.8f), // Màu xanh nhạt
        modifier = Modifier.size(220.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.uthlogo), // **Lưu ý 2**
            contentDescription = "UTH Logo",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun TitleSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "SmartTasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3) // Màu xanh đậm UTH
        )
        Text(
            text = "A simple and efficient to-do app",
            fontSize = 12.sp,
            color = Color(0xFF2196F3)
        )
    }
}

@Composable
private fun WelcomeSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Text(
            text = "Welcome",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ready to explore? Log in to get started.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE3F2FD), // Màu nền nút
            contentColor = Color.Black // Màu chữ và icon
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.gglogo), // **Lưu ý 3**
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "SIGN IN WITH GOOGLE",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun FooterSection() {
    Text(
        text = "© UTHSmartTasks",
        fontSize = 12.sp,
        color = Color.Gray
    )
}


// --- Preview để xem nhanh ---
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}