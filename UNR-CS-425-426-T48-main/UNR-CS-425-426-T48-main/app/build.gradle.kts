plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.senior"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.senior"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.11.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    implementation ("androidx.camera:camera-core:1.4.0-alpha02")
    implementation ("androidx.camera:camera-camera2:1.4.0-alpha02")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha02")
    implementation ("androidx.camera:camera-view:1.4.0-alpha02")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("org.postgresql:postgresql:42.7.2")
    implementation ("com.google.code.gson:gson:2.8.8")


}