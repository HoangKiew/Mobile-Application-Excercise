pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // THÊM DÒNG NÀY VÀO ĐỂ SỬA LỖI
        maven("https://androidx.dev/storage/compose-compiler/repository/")
    }
}
rootProject.name = "Week6_API" // Tên project của bạn
include(":app")