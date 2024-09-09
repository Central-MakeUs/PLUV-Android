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