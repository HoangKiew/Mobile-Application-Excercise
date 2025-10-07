package com.example.mobileapplicationexcercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat

class Bai2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bai2)

        // Ánh xạ các view trong layout
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val tvThongBao = findViewById<TextView>(R.id.tvThongBao)
        val btnKiemTra = findViewById<Button>(R.id.btnKiemTra)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        // Xử lý khi bấm nút "Kiểm tra"
        btnKiemTra.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            when {
                email.isEmpty() -> {
                    tvThongBao.text = "Email không hợp lệ"
                    tvThongBao.setTextColor(
                        ContextCompat.getColor(this, android.R.color.holo_red_dark)
                    )
                }
                !email.contains("@") -> {
                    tvThongBao.text = "Email không đúng định dạng"
                    tvThongBao.setTextColor(
                        ContextCompat.getColor(this, android.R.color.holo_red_dark)
                    )
                }
                else -> {
                    tvThongBao.text = "Bạn đã nhập email hợp lệ"
                    tvThongBao.setTextColor(
                        ContextCompat.getColor(this, android.R.color.holo_green_dark)
                    )
                }
            }
        }
        // Nút quay lại màn hình chính
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
