package com.example.mobileapplicationexcercise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBai1 = findViewById<Button>(R.id.btnBai1)
        val btnBai2 = findViewById<Button>(R.id.btnBai2)
        val btnBai3 = findViewById<Button>(R.id.btnBai3)

        btnBai1.setOnClickListener {
            val intent = Intent(this, Bai1Activity::class.java)
            startActivity(intent)
        }

        btnBai2.setOnClickListener {
            val intent = Intent(this, Bai2Activity::class.java)
            startActivity(intent)
        }

        btnBai3.setOnClickListener {
            val intent = Intent(this, Bai3Activity::class.java)
            startActivity(intent)
        }
    }
}
