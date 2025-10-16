package com.example.week3

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // --- THÊM VÀO ĐÂY ---
                    // Thêm style để làm đậm chữ
                    Text(
                        text = "Text Detail",
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            val styledText = buildAnnotatedString {
                append("The ")
                withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick ")
                }
                withStyle(
                    SpanStyle(
                        color = Color(0xFFB87333),
                        fontSize = 46.sp
                    )
                ) {
                    // Style con bên trong chỉ để in đậm chữ "B"
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("B")
                    }
                    // Chữ "rown" không có style con nên sẽ bình thường
                    append("rown ")
                }
                append("\nfox ")
                withStyle(SpanStyle(letterSpacing = 8.sp)) { append("jumps ") }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 34.sp
                    )
                ) { append("over ") }

                // 👇 CHỈNH LẠI ĐOẠN NÀY ĐỂ GIỐNG ẢNH
                // 1. Gạch chân chữ "the"
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("the ")
                }

                // 2. Dùng FontFamily.Cursive cho chữ "lazy"
                withStyle(
                    SpanStyle(
                        fontFamily = FontFamily.Cursive, // <-- Thêm dòng này để có kiểu chữ viết tay
                        fontStyle = FontStyle.Italic,
                        fontSize = 32.sp
                    )
                ) { append("lazy ") }

                append("dog.")
            }

            Text(
                text = styledText,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 45.sp),
                textAlign = TextAlign.Start,
                lineHeight = 48.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Divider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TextPreview() {
    Week3Theme {
        val navController = rememberNavController()
        TextScreen(navController = navController)
    }
}