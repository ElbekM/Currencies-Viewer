package com.example.currenciesviewer.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceProvider {
    private val baseUrl = "https://www.cbr-xml-daily.ru/"
    private val timeout = 10L

    val currencyRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.i("APP", message)
                }
            }).apply { level = HttpLoggingInterceptor.Level.BODY}

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }
}
