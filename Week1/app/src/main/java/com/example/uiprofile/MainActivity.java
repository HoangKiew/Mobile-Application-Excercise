package com.example.uiprofile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            // Quay lại màn
            finish();
        });

        /* Edit */
        ImageButton btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show());
    }
}
