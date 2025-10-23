package com.example.week4

import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week4.ui.theme.Week4Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ---VIEWMODEL---
val logoBlue = Color(0xFF006EE9)
data class DataFlowUiState(
    val email: String = "",
    val code: String = "",
    val pass: String = "",
    val confirmPass: String = "",
    val showSubmittedInfo: Boolean = false,
    val verificationError: String = ""
)
class DataFlowViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DataFlowUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    //OTP
    fun onCodeChange(code: String) {
        // Chỉ nhận 6 ký tự đầu tiên
        _uiState.update { it.copy(code = code.take(6), verificationError = "") }
    }

    fun onPassChange(pass: String) {
        _uiState.update { it.copy(pass = pass) }
    }

    fun onConfirmPassChange(pass: String) {
        _uiState.update { it.copy(confirmPass = pass) }
    }

    // Hàm mô phỏng check code
    fun verifyCode(): Boolean {
        if (uiState.value.code == "123456") {
            _uiState.update { it.copy(verificationError = "") }
            return true
        } else {
            _uiState.update { it.copy(verificationError = "Mã code không đúng. Thử '123456'.") }
            return false
        }
    }

    // Hàm khi nhấn nút Submit
    fun onSubmit() {
        _uiState.update { it.copy(showSubmittedInfo = true) }
    }
}

// ---ROUTE---
object Routes {
    const val FORGOT = "forgot_screen"
    const val VERIFY = "verify_screen"
    const val RESET = "reset_screen"
    const val CONFIRM = "confirm_screen"
}
@Composable
fun DataFlowScreen(mainNavController: NavController) {
    val navController = rememberNavController()
    val viewModel: DataFlowViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.FORGOT
    ) {
        //Nhập Email
        composable(Routes.FORGOT) {
            ForgotPasswordScreen(
                viewModel = viewModel,
                onNextClick = {
                    navController.navigate(Routes.VERIFY)
                },
                onBackClick = {
                    mainNavController.popBackStack()
                }
            )
        }

        //Xác nhận Code
        composable(Routes.VERIFY) {
            VerifyCodeScreen(
                viewModel = viewModel,
                onNextClick = {
                    if(viewModel.verifyCode()) {
                        navController.navigate(Routes.RESET)
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        //Đặt lại Mật khẩu
        composable(Routes.RESET) {
            ResetPasswordScreen(
                viewModel = viewModel,
                onNextClick = {
                    navController.navigate(Routes.CONFIRM)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        //Xác nhận
        composable(Routes.CONFIRM) {
            ConfirmScreen(
                viewModel = viewModel,
                onSubmitClick = {
                    viewModel.onSubmit()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// ---LAYOUT---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAuthScreen(
    title: String,
    subtitle: String,
    showBackArrow: Boolean = true,
    onBackClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    if (showBackArrow) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = primaryBlue)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.uth),
                contentDescription = "Logo",
                modifier = Modifier.width(80.dp).aspectRatio(1.5f),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "SmartTasks",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = logoBlue
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Tiêu đề
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            content()
        }
    }
}

// ---MÀN HÌNH CON---

//Nhập Email
@Composable
fun ForgotPasswordScreen(
    viewModel: DataFlowViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseAuthScreen(
        title = "Forgot Password?",
        subtitle = "Enter your Email, we will send you a verification code.",
        showBackArrow = false,
        onBackClick = onBackClick
    ) {

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Your Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue), // Dùng biến toàn cục
            enabled = uiState.email.isNotBlank()
        ) {
            Text("Next", fontSize = 16.sp)
        }
    }
}

//Nhập Code
@Composable
fun VerifyCodeScreen(
    viewModel: DataFlowViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseAuthScreen(
        title = "Verify Code",
        subtitle = "Enter the the code we just sent you on your registered Email.",
        onBackClick = onBackClick
    ) {

        OtpTextField(
            otpText = uiState.code,
            onOtpTextChange = { newCode ->
                viewModel.onCodeChange(newCode)
            }
        )

        if (uiState.verificationError.isNotBlank()) {
            Text(
                text = uiState.verificationError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
            enabled = uiState.code.length == 6
        ) {
            Text("Next", fontSize = 16.sp)
        }
    }
}

//Đặt Mật khẩu
@Composable
fun ResetPasswordScreen(
    viewModel: DataFlowViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var passVisible by rememberSaveable { mutableStateOf(false) }
    var confirmVisible by rememberSaveable { mutableStateOf(false) }

    BaseAuthScreen(
        title = "Create new password",
        subtitle = "Your new password must be different form previously used password.",
        onBackClick = onBackClick
    ) {

        OutlinedTextField(
            value = uiState.pass,
            onValueChange = { viewModel.onPassChange(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passVisible = !passVisible }) {
                    Icon(imageVector = image, "Toggle visibility")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.confirmPass,
            onValueChange = { viewModel.onConfirmPassChange(it) },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (confirmVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(imageVector = image, "Toggle visibility")
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
            enabled = (uiState.pass.isNotBlank() && uiState.pass == uiState.confirmPass)
        ) {
            Text("Next", fontSize = 16.sp)
        }
    }
}

//Xác nhận
@Composable
fun ConfirmScreen(
    viewModel: DataFlowViewModel,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseAuthScreen(
        title = "Confirm",
        subtitle = "We are here to help you!",
        onBackClick = onBackClick
    ) {

        // Ô Email (Thêm icon Person)
        OutlinedTextField(
            value = uiState.email,
            onValueChange = {},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Email Icon") },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledLeadingIconColor = Color.Gray, // Thêm màu cho icon
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- THÊM Ô CODE MỚI ---
        OutlinedTextField(
            value = uiState.code, // <-- Lấy code từ ViewModel
            onValueChange = {},
            label = { Text("Code") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Code Icon") }, // Icon phong bì
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledLeadingIconColor = Color.Gray,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "•".repeat(uiState.pass.length),
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledLeadingIconColor = Color.Gray,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSubmitClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
        ) {
            Text("Submit", fontSize = 16.sp)
        }

        if (uiState.showSubmittedInfo) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Đã gửi thông tin:",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text("Email: ${uiState.email}", color = Color.DarkGray)
            Text("Code: ${uiState.code}", color = Color.DarkGray) // <-- ĐÃ THÊM CODE
            Text("Password: ${uiState.pass}", color = Color.DarkGray)
        }
    }
}
@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String) -> Unit
) {
    // Quản lý focus
    val focusRequesters = remember { List(otpCount) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    //Tự động focus vào ô trống đầu tiên khi mới vào màn
    LaunchedEffect(Unit) {
        focusRequesters.getOrNull(otpText.length.coerceAtMost(otpCount - 1))?.requestFocus()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Khoảng cách 8.dp giữa các ô
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 3. Tạo 6 ô
        repeat(otpCount) { index ->
            val char = otpText.getOrNull(index)?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { newValue ->
                    // Logic xử lý khi gõ
                    if (newValue.length <= 1) {
                        if (newValue.isEmpty()) {
                            // --- XỬ LÝ XÓA (BACKSPACE) ---
                            val newOtp = otpText.toMutableList()
                            if(index < newOtp.size) {
                                newOtp.removeAt(index)
                                onOtpTextChange(newOtp.joinToString(""))
                            }
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        } else {
                            val newOtp = otpText.toMutableList()
                            if (index == newOtp.size) {
                                newOtp.add(newValue.first())
                            } else {
                                newOtp[index] = newValue.first()
                            }
                            onOtpTextChange(newOtp.joinToString("").take(otpCount))

                            if (index < otpCount - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                focusManager.clearFocus()
                            }
                        }
                    } else if (newValue.length == otpCount) {
                        // --- XỬ LÝ DÁN (PASTE) ---
                        onOtpTextChange(newValue)
                        focusManager.clearFocus() // Xong, ẩn bàn phím
                    }
                },
                modifier = Modifier
                    .weight(1f) // 6 ô chia đều
                    .aspectRatio(1f) // Làm cho ô vuông
                    .focusRequester(focusRequesters[index]),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp, // Chữ to rõ
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(12.dp), // Bo góc
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataFlowPreview() {
    Week4Theme {
        val navController = rememberNavController()
        DataFlowScreen(mainNavController = navController)
    }
}