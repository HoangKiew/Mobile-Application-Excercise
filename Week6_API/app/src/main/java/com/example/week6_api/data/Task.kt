package com.example.week6_api.data

import com.google.gson.annotations.SerializedName

// Lớp vỏ bọc cho API DANH SÁCH (Bạn đã có)
data class TaskApiResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Task>
)

// --- THÊM LỚP VỎ BỌC MỚI NÀY ---
// Lớp vỏ bọc cho API CHI TIẾT
data class TaskDetailApiResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Task // <-- Chỉ chứa 1 Task
)
// ------------------------------

data class Subtask(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)

data class Attachment(
    @SerializedName("id") val id: Int,
    @SerializedName("fileName") val name: String,
    @SerializedName("fileUrl") val fileUrl: String
)

data class Task(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String,
    @SerializedName("category") val category: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("dueDate") val dateTime: String,
    @SerializedName("subtasks") val subtasks: List<Subtask>? = null,
    @SerializedName("attachments") val attachments: List<Attachment>? = null
)