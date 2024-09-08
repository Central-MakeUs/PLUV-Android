import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.pluv.android.library)
    alias(libs.plugins.pluv.android.hilt)
}


android {
    namespace = "com.cmc15th.pluv.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "server_url", getProperty("server_url"))
        buildConfigField("String", "spotify_client_id", getProperty("spotify_client_id"))
        buildConfigField("String", "spotify_redirect_uri", getProperty("spotify_redirect_uri"))
        buildConfigField("String", "google_auth_client_id", getProperty("google_auth_client_id"))
    }

}

fun getProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit)

}