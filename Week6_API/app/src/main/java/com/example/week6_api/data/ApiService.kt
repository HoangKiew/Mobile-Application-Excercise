package com.example.week6_api.data

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/researchUTH/tasks")
    suspend fun getAllTasks(): Response<List<Task>>

    @GET("api/researchUTH/task/{id}")
    suspend fun getTaskById(@Path("id") taskId: String): Response<Task>

    @DELETE("api/researchUTH/task/{id}")
    suspend fun deleteTaskById(@Path("id") taskId: String): Response<Unit>
}