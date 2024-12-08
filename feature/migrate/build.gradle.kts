plugins {
    alias(libs.plugins.pluv.android.feature)
    //
}

android {
    namespace = "com.cmc15th.pluv.feature.migrate"

}

dependencies {
    implementation(projects.feature.common)
    implementation(libs.google.auth)
//    implementation(libs.spotify.auth)
    implementation(libs.coil.compose)

    implementation(files("../../libs/spotify-auth-release-2.1.0.aar"))
}