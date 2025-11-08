package com.example.week6_api.data

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // (Đã đúng)
    @GET("tasks")
    suspend fun getAllTasks(): Response<TaskApiResponse>

    // SỬA DÒNG NÀY:
    @GET("task/{id}")
    suspend fun getTaskById(@Path("id") taskId: String): Response<TaskDetailApiResponse> // <-- Sửa ở đây

    // (Đã đúng)
    @DELETE("task/{id}")
    suspend fun deleteTaskById(@Path("id") taskId: String): Response<Unit>
}