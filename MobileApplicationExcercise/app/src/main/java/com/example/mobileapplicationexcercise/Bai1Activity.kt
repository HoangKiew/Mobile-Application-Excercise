package com.example.mobileapplicationexcercise

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Bai1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bai1)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        // Nút kiểm tra
        btnCheck.setOnClickListener {
            val name = edtName.text.toString().trim()
            val ageText = edtAge.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và tuổi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            if (age == null) {
                Toast.makeText(this, "Tuổi phải là số hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = when {
                age > 65 -> "Người già"
                age in 6..65 -> "Người lớn"
                age in 2..5 -> "Trẻ em"
                else -> "Em bé"
            }

            tvResult.text = "$name là $category"
        }

        // Nút quay lại màn hình chính
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
