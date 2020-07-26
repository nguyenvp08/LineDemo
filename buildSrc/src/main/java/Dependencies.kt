import Versions.constraintVersion
import Versions.daggerVersion
import Versions.firebaseCrashVersion
import Versions.playServiceAdsVersion
import Versions.playServiceAnalyticsVersion
import Versions.firebaseMessagingVersion
import Versions.glideVersion
import Versions.pagingVersion
import Versions.playCoreVersion
import Versions.playServiceAdIdsVersion
import Versions.playServiceAuthVersion
import Versions.playServiceLocationVersion
import Versions.workManagerVersion

object Versions {
    val playServiceLocationVersion = "17.0.0"
    val playServiceAuthVersion = "18.0.0"
    val playServiceAdsVersion = "19.2.0"
    val playServiceAdIdsVersion = "17.0.0"
    val playServiceAnalyticsVersion = "17.0.0"
    val firebaseMessagingVersion = "20.2.1"
    val firebaseCrashVersion = "16.2.1"
    val constraintVersion = "1.1.3"
    val glideVersion = "4.11.0"
    val daggerVersion = "2.28.1"
    val playCoreVersion = "1.7.3"
    val pagingVersion = "2.1.2"
    val workManagerVersion = "2.2.0"
    val fragmentKtxVersion = "1.2.5"

    val roundedimageview = "2.3.0"
    val retrofit = "2.6.4"
    val facebook = "7.1.0"
    val circleimageview = "3.1.0"

    val roomVersion = "2.2.5"
    val recyclerviewVersion = "1.1.0"

    val gson = "2.8.6"
    val okhttpVersion = "3.12.12"
    val eventBusVersion = "3.1.1"
    val rxjavaVersion = "2.2.19"
    val rxAndroidVersion = "2.1.1"

    val androidXCoreVersion = "1.1.0"
    val appcompatVersion = "1.1.0"
    val materialVersion = "1.1.0"
}

object TestVersions {
    val espressoCore = "3.1.0"
    val junitVersion = "4.12"
    val junitExtVersion = "1.1.1"
}

object ToolVersions {
    val googleService = "4.3.3"
    val androidBuildTool = "3.5.3"
    val gradleVersionsPlugin = "0.27.0"
    val kotlinGradlePlugin = "1.3.72"
}

object Deps {
    val playService = arrayOf(
            "com.google.android.gms:play-services-location:$playServiceLocationVersion"
            , "com.google.firebase:firebase-crash:$firebaseCrashVersion")

    val glideLib = arrayOf("com.github.bumptech.glide:okhttp3-integration:$glideVersion@aar")

    val annotationProcessorLibs = arrayOf("com.github.bumptech.glide:compiler:$glideVersion"
            , "com.google.dagger:dagger-compiler:$daggerVersion")

    val daggerLib = arrayOf("com.google.dagger:dagger:$daggerVersion")
}