plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.example.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movies"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
//    implementation("androidx.compose.ui:ui-test-junit4-android:1.7.8")
    val activityVersion = "1.9.3"
    val roomVersion = "2.5.0"
    val moshiVersion = "1.8.0"
    val composeVersion = "1.7.8"
    val junitVersion = "4.13.2"
    val androidx_test_runner_version = "1.5.2"
    val androidx_test_rules_version = "1.5.0"
    val androidx_test_junit_ktx_version = "1.1.5"
    val mockito_inline_version = "4.0.0"
    val compose_test_version = "1.4.3"
    val navigation_testing_version = "2.3.0"
    val dexmaker_mockito_version = "2.28.1"
    val hiltVersion = "2.44"
    val androidx_work_testing = "2.8.1"
    val kotlinx_coroutines_test_version = "1.6.4"
    val coroutines_version = "1.7.1"
    val mockk_version = "1.12.7"
    val androidx_test_ext = "1.2.0-alpha01"
    val androidx_arch_core_version = "2.2.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.2")

//    coil for loading images
    implementation("io.coil-kt:coil-compose:2.4.0")

//    navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")

    // Room for db
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")

    //Hilt for di
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("com.squareup.inject:assisted-inject-annotations-dagger2:0.5.2")
    kapt("com.squareup.inject:assisted-inject-processor-dagger2:0.5.2")


    // retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // moshi for parsing the JSON format
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

    //okHttp for logging networking
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("androidx.compose.animation:animation:1.7.0-alpha07")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:2023.03.00")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:2023.03.00")
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")

// Testing
    testImplementation("junit:junit:$junitVersion")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("androidx.test.ext:junit:$androidx_test_ext")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation("androidx.arch.core:core-testing:$androidx_arch_core_version")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_test_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_test_version")
    testImplementation("org.mockito:mockito-inline:$mockito_inline_version")

    // Instrumentation
    androidTestImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test:runner:$androidx_test_runner_version")
    androidTestImplementation("androidx.test:rules:$androidx_test_rules_version")
    androidTestImplementation("androidx.test.ext:junit-ktx:$androidx_test_junit_ktx_version")
    androidTestImplementation("androidx.arch.core:core-testing:$androidx_arch_core_version")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:$dexmaker_mockito_version")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinx_coroutines_test_version")
    androidTestImplementation("androidx.work:work-testing:$androidx_work_testing")
    implementation("androidx.navigation:navigation-testing:$navigation_testing_version")
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

}
