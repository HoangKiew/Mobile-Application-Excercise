package com.example.week6

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("price")
    val price: Double,

    @SerialName("des")
    val description: String,

    // --- SỬA Ở ĐÂY ---
    @SerialName("imgURL")
    val imageUrl: String
)