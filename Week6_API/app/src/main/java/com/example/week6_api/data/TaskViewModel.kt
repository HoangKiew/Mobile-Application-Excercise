package com.example.week6_api.data

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val apiService = RetrofitClient.instance

    // (Các biến State giữ nguyên)
    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> = _tasks

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedTask = mutableStateOf<Task?>(null)
    val selectedTask: State<Task?> = _selectedTask

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack = _navigateBack.asSharedFlow()

    init {
        getAllTasks()
    }

    // (Hàm này đã đúng)
    fun getAllTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getAllTasks()
                if (response.isSuccessful) {
                    _tasks.value = response.body()?.data ?: emptyList()
                } else {
                    _tasks.value = emptyList()
                }
            } catch (e: Exception) {
                _tasks.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    // 🟩 2️⃣ Lấy task theo ID (ĐÃ SỬA)
    fun getTaskById(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _selectedTask.value = null // <-- Reset task
            try {
                Log.d("TaskViewModel", "🔹 Fetching task ID = $taskId")

                // Hàm này giờ trả về Response<TaskDetailApiResponse>
                val response = apiService.getTaskById(taskId)

                if (response.isSuccessful) {
                    // --- SỬA DÒNG NÀY ---
                    // Lấy Task từ trường .data của đối tượng vỏ bọc
                    _selectedTask.value = response.body()?.data
                    // ---------------------

                    Log.d("TaskViewModel", "✅ Task loaded: ${_selectedTask.value?.title}")
                } else {
                    Log.e("TaskViewModel", "❌ API error: ${response.code()}")
                    _selectedTask.value = null
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "❌ Exception: ${e.message}")
                _selectedTask.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    // (Hàm này đã đúng)
    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("TaskViewModel", "🔹 Deleting task ID = $taskId")
                val response = apiService.deleteTaskById(taskId)
                if (response.isSuccessful) {
                    Log.d("TaskViewModel", "✅ Task deleted successfully")
                    _navigateBack.emit(Unit)
                    // getAllTasks() // Tạm thời không cần refresh ở đây
                } else {
                    Log.e("TaskViewModel", "❌ Delete failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "❌ Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}