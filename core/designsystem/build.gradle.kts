plugins {
    alias(libs.plugins.pluv.android.library.compose)
    alias(libs.plugins.pluv.android.hilt)
}


android {
    namespace = "com.cmc15th.pluv.core.designsystem"
}

dependencies {
    implementation(libs.coil.compose)
}