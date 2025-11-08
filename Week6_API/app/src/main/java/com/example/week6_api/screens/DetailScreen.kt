package com.example.week6_api.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter // <-- Sửa: Import Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week6_api.R // Import R
import com.example.week6_api.data.Attachment
import com.example.week6_api.data.Subtask
import com.example.week6_api.data.Task
import com.example.week6_api.data.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: TaskViewModel,
    taskId: String
) {
    val task by viewModel.selectedTask
    val isLoading by viewModel.isLoading

    LaunchedEffect(key1 = taskId) {
        viewModel.getTaskById(taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateBack.collectLatest {
            viewModel.getAllTasks()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                onBackClick = { navController.popBackStack() },
                onDeleteClick = { viewModel.deleteTask(taskId) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                task != null -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            TaskDetailHeader(task = task!!)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            DetailInfoCard(task = task!!)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        if (!task!!.subtasks.isNullOrEmpty()) {
                            item {
                                Text("Subtasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(task!!.subtasks!!) { subtask ->
                                SubtaskItem(subtask = subtask)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        if (!task!!.attachments.isNullOrEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Attachments", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(task!!.attachments!!) { attachment ->
                                AttachmentItem(attachment = attachment)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
                else -> {
                    Text("Không tìm thấy task.", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

// --- CÁC COMPOSABLE CON CHO DETAILSCREEN ---

@Composable
fun DetailTopBar(onBackClick: () -> Unit, onDeleteClick: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF007AFF) // Màu xanh
                )
            }
            Text(
                text = "Detail",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(Color(0xFFFFDEDD)) // Màu cam/hồng nhạt
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_del), // <-- Dùng icon của bạn
                    contentDescription = "Delete",
                    modifier = Modifier.padding(6.dp).size(20.dp),
                    colorFilter = ColorFilter.tint(Color.Red)
                )
            }
        }
    }
}

@Composable
fun TaskDetailHeader(task: Task) {
    Text(
        text = task.title ?: "", // <-- SỬA: Thêm ?: ""
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = task.description ?: "", // <-- SỬA: Thêm ?: ""
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
fun DetailInfoCard(task: Task) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFFFDCFE8), // Màu hồng
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoColumn(
                painter = painterResource(id = R.drawable.icon_category),
                text = task.category ?: "" // <-- SỬA: Thêm ?: ""
            )
            InfoColumn(
                painter = painterResource(id = R.drawable.icon_status),
                text = task.status ?: "" // <-- SỬA: Thêm ?: ""
            )
            InfoColumn(
                painter = painterResource(id = R.drawable.icon_priority),
                text = task.priority ?: "" // <-- SỬA: Thêm ?: ""
            )
        }
    }
}

@Composable
fun InfoColumn(painter: Painter, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painter,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun SubtaskItem(subtask: Subtask) {
    var isChecked by remember { mutableStateOf(subtask.isCompleted) }

    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Black
                )
            )
            Text(
                text = subtask.title ?: "", // <-- SỬA: Thêm ?: ""
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_attachment),
                contentDescription = "Attachment",
                colorFilter = ColorFilter.tint(Color.Gray), // <-- Đã sửa (lỗi từ lần trước)
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = attachment.name ?: "", // <-- SỬA: Thêm ?: ""
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

// --- PREVIEWS CHO DETAILSCREEN (Dùng Int cho ID) ---

val sampleSubtasks = listOf(
    Subtask(id = 11, title = "Team Meeting", isCompleted = true),
    Subtask(id = 12, title = "Prepare presentation slides", isCompleted = false)
)
val sampleAttachments = listOf(
    Attachment(id = 100, name = "document_1_0.pdf", fileUrl = "https://example.com/doc.pdf")
)
val sampleTaskForDetail = Task(
    id = 1,
    title = "Complete Android Project",
    description = "Finish the UI, integrate API, and write documentation",
    status = "In Progress",
    category = "Work",
    priority = "High",
    dateTime = "14:00 2500-03-26",
    subtasks = sampleSubtasks,
    attachments = sampleAttachments
)

@Preview(showBackground = true)
@Composable
fun PreviewDetailTopBar() {
    DetailTopBar(onBackClick = {}, onDeleteClick = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreenLayout() {
    Scaffold(
        topBar = { DetailTopBar(onBackClick = {}, onDeleteClick = {}) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                TaskDetailHeader(task = sampleTaskForDetail)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                DetailInfoCard(task = sampleTaskForDetail)
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Text("Subtasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(sampleTaskForDetail.subtasks!!) { subtask ->
                SubtaskItem(subtask = subtask)
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Attachments", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(sampleTaskForDetail.attachments!!) { attachment ->
                AttachmentItem(attachment = attachment)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}