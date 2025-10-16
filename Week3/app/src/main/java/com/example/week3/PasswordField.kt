package com.example.week3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week3.ui.theme.Week3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldScreen(navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var submissionStatus by rememberSaveable { mutableStateOf<Boolean?>(null) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Password Field",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF007AFF)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF007AFF)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(270.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    submissionStatus = null
                },
                label = { Text("Mật khẩu") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (password.isNotBlank()) {
                            submissionStatus = true
                        } else {
                            submissionStatus = false
                        }
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = {
                    // Dòng code này giờ sẽ hoạt động bình thường
                    val image = if (passwordVisible)
                        Icons.Filled.VisibilityOff
                    else
                        Icons.Filled.Visibility

                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val (message, messageColor) = when (submissionStatus) {
                true -> "Nhập mật khẩu thành công" to Color(0xFF34C759)
                false -> "Mật khẩu không được để trống" to MaterialTheme.colorScheme.error
                null -> "" to Color.Transparent
            }
            Text(
                text = message,
                color = messageColor,
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview() {
    Week3Theme {
        PasswordFieldScreen(navController = rememberNavController())
    }
}