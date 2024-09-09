plugins {
    alias(libs.plugins.pluv.android.feature)
    //
}

android {
    namespace = "com.cmc15th.pluv.feature.migrate"

}

dependencies {
    api(projects.feature.common)
    implementation(libs.google.auth)
    implementation(libs.spotify.auth)
    implementation(libs.coil.compose)
}