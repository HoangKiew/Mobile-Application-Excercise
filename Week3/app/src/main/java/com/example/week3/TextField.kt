package com.example.week3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(navController: NavController) {
    var text by rememberSaveable { mutableStateOf("") }
    val submittedTexts = remember { mutableStateListOf<String>() }
    var lastSubmissionValid by rememberSaveable { mutableStateOf<Boolean?>(null) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "TextField",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF007AFF)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF007AFF)
                        )
                    }
                }
            )
        }
    )  { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // --- THAY ĐỔI 1: TẠO KHU VỰC NHẬP LIỆU CHIẾM 1/3 MÀN HÌNH ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(270.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        lastSubmissionValid = null
                    },
                    label = { Text("Thông tin nhập") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (text.isNotBlank()) {
                                submittedTexts.add(0, text)
                                lastSubmissionValid = true
                                text = ""
                            } else {
                                lastSubmissionValid = false
                            }
                            focusManager.clearFocus()
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                val (message, messageColor) = when (lastSubmissionValid) {
                    true -> "Nhập dữ liệu thành công" to Color(0xFF34C759)
                    false -> "Xin hãy nhập dữ liệu" to MaterialTheme.colorScheme.error
                    null -> "" to Color.Transparent
                }
                Text(
                    text = message,
                    color = messageColor,
                    modifier = Modifier.height(20.dp)
                )
            }

            // --- THAY ĐỔI 2: DANH SÁCH CHIẾM 2/3 MÀN HÌNH CÒN LẠI ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                items(submittedTexts) { submittedText ->
                    Text(
                        text = submittedText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    Week3Theme {
        TextFieldScreen(navController = rememberNavController())
    }
}