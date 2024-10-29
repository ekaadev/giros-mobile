package eka.giros.repository

import android.os.Build
import eka.giros.BuildConfig
import eka.giros.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val BASE_URL = if (Build.FINGERPRINT.contains("generic")) {
        BuildConfig.AVD_BASE_URL
    } else {
        BuildConfig.PHYSICAL_DEVICE_BASE_URL
    }

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}