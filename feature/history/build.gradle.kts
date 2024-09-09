plugins {
    alias(libs.plugins.pluv.android.feature)
}

android {
    namespace = "com.cmc15th.pluv.feature.history"

}

dependencies {
    implementation(libs.coil.compose)
}