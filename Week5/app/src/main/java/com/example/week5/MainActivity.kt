package com.example.week5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week5.ui.screens.LoginScreen
import com.example.week5.ui.screens.ProfileScreen
import com.example.week5.ui.theme.Week5Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val webClientId by lazy {
        getString(R.string.default_web_client_id)
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Toast.makeText(this, "Google Sign-In success. Logging in to Firebase...", Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Google Sign-In cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            Week5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    private fun AppNavigation() {
        val navController = rememberNavController()
        // *** SỬA LỖI: Cập nhật lại logic điều hướng sau khi đăng nhập
        val startDestination = if (auth.currentUser != null) "profile" else "login"

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                LoginScreen(onSignInClick = { signIn() })
            }
            composable("profile") {
                ProfileScreen(
                    user = auth.currentUser,
                    onSignOutClick = { signOut(navController) }
                )
            }
        }

        // Lắng nghe sự thay đổi trạng thái đăng nhập để điều hướng
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                navController.navigate("profile") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun signOut(navController: NavHostController) {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            navController.navigate("login") {
                popUpTo("profile") { inclusive = true }
                launchSingleTop = true // Tránh tạo lại màn hình login nếu đã có
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Firebase sign-in successful", Toast.LENGTH_SHORT).show()
                    // Không cần làm gì ở đây nữa, AuthStateListener sẽ tự động điều hướng
                } else {
                    // *** SỬA LỖI: Thay LONG_SHOW thành LENGTH_LONG ***
                    Toast.makeText(this, "Firebase sign-in failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
