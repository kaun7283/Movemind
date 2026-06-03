plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace         = "com.movemind.app"
    compileSdk        = 35

    defaultConfig {
        applicationId = "com.movemind.app"
        minSdk        = 26
        targetSdk     = 35
        versionCode   = 1
        versionName   = "1.0.0"
    }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // ── Compose BOM (alinha versões automaticamente) ──────────────────────
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // ── Core & Activity ────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ── Compose UI ────────────────────────────────────────────────────────
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // ── Material 3 ────────────────────────────────────────────────────────
    implementation(libs.androidx.material3)

    // ── Material Icons Extended (FitnessCenter, WaterDrop, etc.) ──────────
    implementation("androidx.compose.material:material-icons-extended")

    // ── Animações ─────────────────────────────────────────────────────────
    implementation(libs.androidx.animation)

    // ── Debug ─────────────────────────────────────────────────────────────
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
