plugins {
    alias(libs.plugins.pluv.android.library)
    alias(libs.plugins.pluv.android.hilt)
}


android {
    namespace = "com.cmc15th.pluv.core.datastore"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences)
}