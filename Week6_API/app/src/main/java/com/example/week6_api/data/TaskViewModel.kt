package com.example.week6_api.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val apiService = RetrofitClient.instance

    // Trạng thái cho HomeScreen
    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> = _tasks

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Trạng thái cho DetailScreen
    private val _selectedTask = mutableStateOf<Task?>(null)
    val selectedTask: State<Task?> = _selectedTask

    // Tín hiệu để điều hướng
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack = _navigateBack.asSharedFlow()

    init {
        getAllTasks()
    }

    // 1. Lấy tất cả tasks
    fun getAllTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getAllTasks()
                if (response.isSuccessful) {
                    _tasks.value = response.body() ?: emptyList()
                } else {
                    _tasks.value = emptyList()
                }
            } catch (e: Exception) {
                _tasks.value = emptyList() // Xử lý lỗi mạng
            }
            _isLoading.value = false
        }
    }

    // 2. Lấy 1 task theo ID
    fun getTaskById(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _selectedTask.value = null
            try {
                val response = apiService.getTaskById(taskId)
                if (response.isSuccessful) {
                    _selectedTask.value = response.body()
                }
            } catch (e: Exception) {
                // Xử lý lỗi
            }
            _isLoading.value = false
        }
    }

    // 3. Xóa 1 task
    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.deleteTaskById(taskId)
                if (response.isSuccessful) {
                    _navigateBack.emit(Unit) // Gửi tín hiệu quay về
                }
            } catch (e: Exception) {
                // Xử lý lỗi
            }
            _isLoading.value = false
        }
    }
}