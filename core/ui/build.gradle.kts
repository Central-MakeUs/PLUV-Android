plugins {
    alias(libs.plugins.pluv.android.library)
    alias(libs.plugins.pluv.android.library.compose)
}


android {
    namespace = "com.cmc15th.pluv.core.ui"
}

dependencies {
    implementation(projects.core.designsystem)
}