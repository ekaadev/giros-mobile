package eka.giros.repository

import android.os.Build
import eka.giros.BuildConfig
import eka.giros.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun isRunningOnEmulator(): Boolean {
        return Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86")
    }

    private val BASE_URL = if (isRunningOnEmulator()) {
        BuildConfig.AVD_BASE_URL
    } else {
        BuildConfig.PHYSICAL_DEVICE_BASE_URL
    }

//    private val BASE_URL = "http://10.0.2.2:3001/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}