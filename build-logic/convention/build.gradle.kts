plugins {
    `kotlin-dsl`
}

group = "com.cmc15th.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.compose.compiler.extension)
}

gradlePlugin {
    val pluginClasses = listOf(
        "android.application" to "AndroidApplicationConventionPlugin",
        "android.application.compose" to "AndroidApplicationComposeConventionPlugin",
        "android.library" to "AndroidLibraryConventionPlugin",
        "android.library.compose" to "AndroidLibraryComposeConventionPlugin",
        "android.hilt" to "AndroidHiltConventionPlugin",
        "android.feature" to "AndroidFeatureConventionPlugin",
    )

    plugins {
        pluginClasses.forEach { plugin -> register(plugin) }
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.register(plugin: Pair<String, String>) {
    val (pluginName, className) = plugin
    register(pluginName) {
        id = "pluv.${pluginName}"
        implementationClass = className
    }
}