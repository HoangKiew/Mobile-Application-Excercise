plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Đảm bảo bạn có plugin compose
}

android {
    namespace = "com.example.week6_api"
    compileSdk = 36 // <-- Sửa từ 34 thành 36

    defaultConfig {
        applicationId = "com.example.week6_api"
        minSdk = 24
        targetSdk = 36 // <-- Sửa từ 34 thành 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    // THÊM KHỐI NÀY VÀO
    composeOptions {
        // Phiên bản này đi với Compose 1.6.x
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}


dependencies {
    // Retrofit (OK)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0") // Cập nhật phiên bản mới nhất

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Material 2
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // --- CÁC THƯ VIỆN CƠ BẢN ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Dùng BOM (Bill of Materials) để quản lý phiên bản Compose
    // Đảm bảo phiên bản BOM trong libs.versions.toml tương thích với kotlinCompilerExtensionVersion
    // Ví dụ: compose-bom:2024.05.00
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Khai báo các thư viện Compose mà không cần phiên bản
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // SỬA LỖI Ở ĐÂY: Thêm thư viện runtime đúng cách
    implementation("androidx.compose.runtime:runtime")

    // Test (OK)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
