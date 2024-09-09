plugins {
    alias(libs.plugins.pluv.android.feature)
}

android {
    namespace = "com.cmc15th.pluv.feature.login"
}

dependencies {
    implementation(projects.feature.common)
    implementation(libs.google.auth)
    implementation(libs.spotify.auth)
}