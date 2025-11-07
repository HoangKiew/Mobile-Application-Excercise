package com.example.week6_api.screens

// TẤT CẢ CÁC IMPORT CẦN THIẾT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // Dùng Material 2
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week6_api.R
import com.example.week6_api.data.Attachment // Import Attachment
import com.example.week6_api.data.Subtask // Import Subtask
import com.example.week6_api.data.Task
import com.example.week6_api.data.TaskViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    // Dòng này đã đúng. Nó tự động "lắng nghe" thay đổi
    val tasks = viewModel.tasks.value
    val isLoading = viewModel.isLoading.value

    Scaffold(
        backgroundColor = Color(0xFFF5F8FF),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                shape = CircleShape,
                backgroundColor = Color(0xFF007AFF)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = { BottomNavBar() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopHeader() // Header với logo và chuông

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                tasks.isEmpty() -> {
                    EmptyView() // Màn hình trống
                }
                else -> {
                    // Màn hình danh sách
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        items(tasks) { task ->
                            TaskCard(
                                task = task,
                                onTaskClick = {
                                    // Chuyển sang màn hình chi tiết với ID
                                    navController.navigate("detail/${task.id}")
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

// --- CÁC COMPOSABLE CON ĐƯỢC GỘP VÀO ---

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                // ĐÃ SỬA LẠI: Tên file là 'logouth' (theo ảnh thư mục res)
                painter = painterResource(id = R.drawable.uthlogo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "SmartTasks",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // <-- Đã sửa lại thành màu đen
                )
                Text(
                    text = "A simple and efficient to-do app",
                    fontSize = 14.sp,
                    color = Color.Gray // <-- Đã sửa lại thành màu xám
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.icon_ring),
            contentDescription = "Notifications",
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun TaskCard(task: Task, onTaskClick: () -> Unit) {
    val cardColor = when (task.status.lowercase()) {
        "in progress" -> Color(0xFFFDCFE8) // Pink
        "pending" -> Color(0xFFE6F5D0) // Green
        else -> Color(0xFFD0E6F5) // Blue
    }

    val isChecked = task.status.lowercase() != "pending"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick() },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = cardColor,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { /* Tạm thời không cho đổi */ },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(task.description, fontSize = 14.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Status: ${task.status}", fontSize = 13.sp, color = Color.Black)
                    // Hiển thị dateTime (đã được parse từ "dueDate")
                    Text(task.dateTime, fontSize = 13.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun BottomNavBar() {
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color.White,
        contentColor = Color.Gray,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF007AFF))
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.DateRange, contentDescription = "Calendar")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.ListAlt, contentDescription = "Tasks")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xFFF5F5F5) // Màu xám nhạt
        ) {
            Column(
                modifier = Modifier.padding(vertical = 48.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Đảm bảo bạn có file "icon_empty_task.png" trong "res/drawable"
                Image(
                    painter = painterResource(id = R.drawable.icon_empty_task),
                    contentDescription = "No Tasks",
                    modifier = Modifier.size(100.dp),
                    colorFilter = ColorFilter.tint(Color.DarkGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No Tasks Yet!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Stay productive—add something to do",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// --- PREVIEWS CHO HOMESCREEN (Đã sửa id = 1) ---

val sampleTask = Task(
    id = 1, // <-- SỬA LẠI THÀNH Int
    title = "Complete Android Project",
    description = "Finish the UI, integrate API, and write documentation",
    status = "In Progress",
    category = "Work",
    priority = "High",
    dateTime = "14:00 2500-03-26",
    subtasks = emptyList(),
    attachments = emptyList()
)

@Preview(showBackground = true)
@Composable
fun PreviewTaskCard() {
    TaskCard(
        task = sampleTask,
        onTaskClick = {}
    )
}
// ... (code PreviewEmptyView giữ nguyên) ...