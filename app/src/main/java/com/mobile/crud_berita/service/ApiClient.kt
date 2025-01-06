package com.mobile.crud_berita.service

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Arrays

object ApiClient {
    private const val BASE_URL = "http://192.168.18.200/beritaDb/"

    val retrofit: BeritaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeritaService::class.java)
    }

    //buat method interceptor

    private val client  = OkHttpClient.Builder()
        .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        .addInterceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    fun interceptor() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    //panggil product service
    //val beritaService : BeritaService by lazy {
        //retrofit.create(BeritaService::class.java)
    //}
}