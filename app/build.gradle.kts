plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.evan.madkotlinweb3wallet"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.evan.madkotlinweb3wallet"
        minSdk = 28
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose 物料清单 (BOM)：统一管理所有 Compose 库的版本，确保它们彼此兼容
    implementation(platform(libs.androidx.compose.bom))
    // Activity 与 Compose 的集成：提供 setContent 等方法，是 Activity 加载 Compose 界面的桥梁
    implementation(libs.androidx.activity.compose)
    // Material Design 3 组件库：提供按钮、卡片、脚手架 (Scaffold) 等现代 UI 组件
    implementation(libs.androidx.compose.material3)
    // Compose 核心 UI 库：包含布局 (Column, Row, Box)、绘图、输入处理等基础功能
    implementation(libs.androidx.compose.ui)
    // Compose 图形库：用于处理颜色、矢量图、渲染效果等图形相关功能
    implementation(libs.androidx.compose.ui.graphics)
    // Compose 预览支持：让 @Preview 注解生效，在 Android Studio 中实时查看 UI
    implementation(libs.androidx.compose.ui.tooling.preview)
    // Android 核心 Kotlin 扩展：为 Android 框架 API 提供更简洁的 Kotlin 写法
    implementation(libs.androidx.core.ktx)
    // 生命周期运行时 Kotlin 扩展：让 Compose 能够感知 Activity/Fragment 的生命周期变化
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // ViewModel 在 Compose 中的支持：提供 viewModel() 方法
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose Navigation: 处理页面间的跳转与参数传递
    implementation(libs.androidx.navigation.compose)
    // Material Icons 扩展库：提供更多的系统图标（如 ArrowDropDown）
    implementation("androidx.compose.material:material-icons-extended")
    // Coil: 图片加载库，用于加载网络上的狐狸图标
    implementation(libs.coil.compose)

    // 单元测试库：用于编写纯逻辑的测试代码（在 JVM 上运行）
    testImplementation(libs.junit)
    // UI 测试相关的 BOM
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Compose UI 测试库：使用 JUnit4 框架对 Compose 界面进行自动化交互测试
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    // Espresso 测试框架：Android 经典的 UI 测试库，常用于模拟点击和滑动
    androidTestImplementation(libs.androidx.espresso.core)
    // Android JUnit 运行器：在真机或模拟器上运行 JUnit 测试的扩展库
    androidTestImplementation(libs.androidx.junit)

    // 测试清单文件支持：在测试模式下自动向 AndroidManifest 注入必要的测试 Activity
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Compose 调试工具：提供布局检查器 (Layout Inspector) 等开发调试增强功能
    debugImplementation(libs.androidx.compose.ui.tooling)
}