package com.example.mobileapplicationexcercise

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat

class Bai3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bai3)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val edtNumber = findViewById<EditText>(R.id.edtNumber)
        val btnTao = findViewById<Button>(R.id.btnTao)
        val tvThongBao = findViewById<TextView>(R.id.tvThongBao)
        val container = findViewById<ConstraintLayout>(R.id.containerNumbers)

        // Nút quay lại
        btnBack.setOnClickListener {
            finish()
        }

        // Nút tạo danh sách
        btnTao.setOnClickListener {
            val input = edtNumber.text.toString().trim()
            tvThongBao.text = ""
            container.removeAllViews()

            val n = input.toIntOrNull()
            if (n == null || n <= 0) {
                tvThongBao.text = "Dữ liệu bạn nhập không hợp lệ"
                tvThongBao.setTextColor(Color.RED)
                return@setOnClickListener
            }

            // Tạo danh sách nút từ 1 → n
            var previousId = View.NO_ID
            for (i in 1..n) {
                val btn = Button(this).apply {
                    id = View.generateViewId()
                    text = i.toString()
                    setTextColor(Color.WHITE)
                    textSize = 18f
                    background = ContextCompat.getDrawable(this@Bai3Activity, R.drawable.rounded_gray_box)
                    setBackgroundColor(Color.parseColor("#E53935"))
                    setPadding(0, 30, 0, 30)
                }

                val params = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    startToStart = container.id
                    endToEnd = container.id
                    topMargin = 16
                    if (previousId == View.NO_ID)
                        topToTop = container.id
                    else
                        topToBottom = previousId
                }

                container.addView(btn, params)
                previousId = btn.id
            }
        }
    }
}
