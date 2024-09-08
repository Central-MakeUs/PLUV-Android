plugins {
    alias(libs.plugins.pluv.android.library)
    alias(libs.plugins.pluv.android.hilt)
}

android {
    namespace = "com.cmc15th.pluv.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(libs.kotlinx.coroutines.core)
}