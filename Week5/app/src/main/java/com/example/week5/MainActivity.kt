package com.example.week5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.week5.ui.screens.LoginScreen
import com.example.week5.ui.theme.Week5Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    // Khai báo Firebase Auth
    private lateinit var auth: FirebaseAuth

    // 1. LẤY WEB CLIENT ID TỪ FILE JSON CỦA EM
    // (Từ "client_id": "..." với "client_type": 3)
    private val webClientId = "141845407169-dot2fioea3fga69lfro14896ctbo4jil.apps.googleusercontent.com"

    // 2. TẠO "LAUNCHER" ĐỂ CHỜ KẾT QUẢ ĐĂNG NHẬP
    // Đây là nơi nhận kết quả trả về từ màn hình chọn Google Account
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Lấy tài khoản Google thành công
                val account = task.getResult(ApiException::class.java)!!

                // Thông báo: Lấy Google Account thành công
                Toast.makeText(this, "Đã lấy Google Account. Đang đăng nhập Firebase...", Toast.LENGTH_SHORT).show()

                // 4. Dùng token của Google Account để đăng nhập Firebase
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Lỗi
                Toast.makeText(this, "Lỗi Google Sign-In: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            // Người dùng hủy
            Toast.makeText(this, "Đăng nhập bị hủy", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo Auth
        auth = Firebase.auth

        // Cấu hình Google Sign-In Options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId) // Dùng Web Client ID
            .requestEmail()
            .build()

        // Tạo Google Sign-In Client
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            Week5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gọi màn hình Login của chúng ta
                    LoginScreen(
                        onSignInClick = {
                            // 3. KHI NHẤN NÚT, GỌI LAUNCHER ĐỂ MỞ MÀN HÌNH CHỌN TÀI KHOẢN
                            val signInIntent = googleSignInClient.signInIntent
                            googleSignInLauncher.launch(signInIntent) // <-- ĐÂY LÀ HÀNH ĐỘNG CLICK
                        }
                    )
                }
            }
        }
    }

    // 5. HÀM ĐĂNG NHẬP VÀO FIREBASE
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Đăng nhập Firebase thành công!
                    val user = auth.currentUser

                    // Thông báo kết quả (Firebase thành công)
                    Toast.makeText(this, "Firebase: Đăng nhập thành công! (User: ${user?.email})", Toast.LENGTH_LONG).show()

                    // TODO: Chuyển sang màn hình ProfileScreen

                } else {
                    // Lỗi đăng nhập Firebase
                    Toast.makeText(this, "Firebase Lỗi: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}