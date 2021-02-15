# MobileAssignment
An Android App that used the Traffic Scotland RSS feed to create a presentable and useful layout of information to users. Note. The plan for journey function does not work as that requires a premium google maps key which would link to my debit/credit card.

# Gradle
```
apply plugin: 'com.android.application'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "org.yuhonglee.gcu.mobileassignment"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.google.android.material:material:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'
    implementation 'com.google.android.gms:play-services-maps:16.1.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.0.0'
    implementation 'com.google.maps.android:android-maps-utils:0.4+'
}
```
# Notes

Within this respoitory is the code for the Mobile App as well as the documentation.

# Application Architecture

IDE: Android Studio
Programming Language: Java

# Demo App

! [] (TrafficApp.gif)
