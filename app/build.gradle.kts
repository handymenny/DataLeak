import com.diffplug.spotless.LineEnding
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.mikepenz.aboutlibraries)
    alias(libs.plugins.diffplug.spotless)
}

android {
    namespace = "eu.amennillo.dataleak"
    compileSdk = 35

    defaultConfig {
        applicationId = "eu.amennillo.dataleak"
        minSdk = 21
        targetSdk = 35
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

configurations { all { exclude(group = "commons-logging", module = "commons-logging") } }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.opencsv.opencsv)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.mikepenz.aboutlibraries.compose.m3)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

aboutLibraries {
    // define the path configuration files are located in. E.g. additional libraries, licenses to
    // add to the target .json
    configPath = "config"

    // Don't fetch remote license
    fetchRemoteLicense = false

    // Enable the duplication mode, allows to merge, or link dependencies which relate
    duplicationMode = DuplicateMode.MERGE
    // Configure the duplication rule, to match "duplicates" with
    duplicationRule = DuplicateRule.SIMPLE
}

spotless {
    format("misc") {
        target("*.md", ".gitignore", "**/*.csv", "**/*.xml")
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }

    kotlin { ktfmt().kotlinlangStyle() }

    kotlinGradle { ktfmt().kotlinlangStyle() }

    // all src files have linux line endings
    lineEndings = LineEnding.UNIX
}
