plugins {
    alias(libs.plugins.pluv.android.feature)
}

android {
    namespace = "com.cmc15th.pluv.feature.mypage"

}

dependencies {
    implementation(projects.feature.common)
//    implementation(libs.spotify.auth)
    implementation(libs.google.auth)
    implementation(files("../../libs/spotify-auth-release-2.1.0.aar"))

}