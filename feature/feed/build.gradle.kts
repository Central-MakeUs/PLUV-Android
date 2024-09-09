plugins {
    alias(libs.plugins.pluv.android.feature)
}

android {
    namespace = "com.cmc15th.pluv.feature.feed"
}

dependencies {
    implementation(libs.coil.compose)
}