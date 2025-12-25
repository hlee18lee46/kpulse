package com.example.k_pulse

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val req = chain.request().newBuilder()
                .addHeader("apikey", SupabaseConfig.PUBLISHABLE_KEY)
                .addHeader("Authorization", "Bearer ${SupabaseConfig.PUBLISHABLE_KEY}")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(req)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(SupabaseConfig.REST_BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
