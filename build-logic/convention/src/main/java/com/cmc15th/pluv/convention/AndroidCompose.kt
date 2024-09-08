package com.cmc15th.pluv.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findBundle("androidx.compose").get())
            add("androidTestImplementation", platform(bom))
            add("debugImplementation", libs.findLibrary("androidx.ui.tooling.preview").get())
        }
    }
}
