import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.pluv.android.feature)
}

android {
    namespace = "com.cmc15th.pluv.feature.common"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "google_auth_client_id", getProperty("google_auth_client_id"))
        buildConfigField("String", "spotify_client_id", getProperty("spotify_client_id"))
        buildConfigField("String", "spotify_redirect_uri", getProperty("spotify_redirect_uri"))
    }
}

fun getProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}

dependencies {
    implementation(libs.google.auth)
    implementation(libs.spotify.auth)
}