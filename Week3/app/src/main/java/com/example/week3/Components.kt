package com.example.week3

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme
import kotlinx.coroutines.delay

data class ComponentItem(val name: String, val desc: String, val route: String, val category: String)

private val sampleComponents = listOf(
    ComponentItem("Text", "Displays text", "text", "Display"),
    ComponentItem("Images", "Displays an image", "images", "Display"),
    ComponentItem("TextField", "Input field for text", "textfield", "Input"),
    ComponentItem("PasswordField", "Input field for passwords", "passwordfield", "Input"),
    ComponentItem("Column", "Arranges elements vertically", "column", "Layout"),
    ComponentItem("Row", "Arranges elements horizontally", "row", "Layout")
)

@Composable
fun ComponentsScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var components by remember { mutableStateOf<List<ComponentItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        delay(1000)
        components = sampleComponents
        isLoading = false
    }

    if (isLoading) {
        SkeletonListComponent()
    } else {
        val groupedComponents = remember(components) {
            components.groupBy { it.category }
        }
        LoadedListComponent(groupedComponents, navController)
    }
}


/* ------------------------- MÀN HÌNH LOADING (SKELETON) -------------------------- */

@Composable
fun SkeletonListComponent() {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(6) {
            SkeletonItem()
        }
    }
}

@Composable
fun SkeletonItem() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.5f)
                    .shimmerBackground()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerBackground()
            )
        }
    }
}

/* ------------------------- MÀN HÌNH SAU KHI LOAD XONG -------------------------- */

@Composable
fun LoadedListComponent(
    components: Map<String, List<ComponentItem>>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "UI Components List",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF007AFF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            components.forEach { (category, items) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(items) { item ->
                    ComponentCard(item, navController)
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/* ------------------------- COMPONENT DÙNG CHUNG -------------------------- */

@Composable
fun ComponentCard(item: ComponentItem, navController: NavController) {
    Button(
        onClick = { navController.navigate(item.route) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB8E6FE)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 4.dp)
        ) {
            // Chữ tiêu đề màu đen đậm
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold // <-- Thêm dòng này
                ),
                color = Color(0xFF212121)
            )
            // Chữ mô tả màu xám
            Text(
                text = item.desc,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF757575)
            )
        }
    }
}

/* ------------------------- HIỆU ỨNG SHIMMER (ĐÃ TỐI ƯU) -------------------------- */

fun Modifier.shimmerBackground(shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp)): Modifier = composed {
    // State để lưu kích thước của Composable
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "shimmerTransition")

    // Animate giá trị X của điểm bắt đầu gradient
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(), // Bắt đầu từ bên ngoài bên trái
        targetValue = 2 * size.width.toFloat(),  // Kết thúc ở bên ngoài bên phải
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200)
        ),
        label = "shimmer"
    )

    this
        .clip(shape)
        .onSizeChanged {
            // Lấy kích thước thực tế của Composable khi nó được vẽ
            size = it
        }
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.6f),
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.6f),
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
}

/* ------------------------- PREVIEW -------------------------- */

@Preview(showBackground = true)
@Composable
fun ComponentsPreview() {
    Week3Theme {
        ComponentsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Skeleton Preview")
@Composable
fun SkeletonPreview() {
    Week3Theme {
        SkeletonListComponent()
    }
}