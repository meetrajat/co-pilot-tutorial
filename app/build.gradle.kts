plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Add this line for kapt support
    id("com.apollographql.apollo3").version("3.8.3")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

apollo {
    service("MyGraphqlService") { // Name your service
        packageName.set("com.example.a29th_mar_android_project") // Replace with your package
        srcDir("src/main/graphql/countriesincontinent") // Where to save the generated code")  //Where to look for .graphql files. Replace the path if necessary
    }
}

android {
    namespace = "com.example.a29th_mar_android_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.a29th_mar_android_project"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.ktx)
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Check for latest version
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Or other converter
    implementation("com.squareup.okhttp3:okhttp:4.11.0") // Check for latest version
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // Check for latest version
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-android:5.5.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

}
