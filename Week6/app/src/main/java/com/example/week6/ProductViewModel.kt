package com.example.week6

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    // 1. Tạo Ktor Client
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    // 2. Tạo State để UI quan sát
    val productState = mutableStateOf<Product?>(null)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    // 3. Hàm để gọi API
    // 3. Hàm để gọi API
    fun fetchProduct() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // --- SỬA Ở ĐÂY ---
                // Chúng ta không nhận về List<Product> nữa
                // mà nhận về 1 Product duy nhất
                val product = client.get("https://mock.apidog.com/m1/890655-872447-default/v2/product")
                    .body<Product>() // <-- Bỏ List<> đi

                // Gán trực tiếp sản phẩm đó vào state
                productState.value = product
                errorMessage.value = null
                // --- KẾT THÚC SỬA ---

            } catch (e: Exception) {
                // Xử lý lỗi
                errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // 4. Gọi API ngay khi ViewModel được tạo
    init {
        fetchProduct()
    }
}