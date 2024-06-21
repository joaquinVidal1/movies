package com.example.movies.data.network

import com.example.movies.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    fun getBuilder(
    ): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            // Request
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("AUTHORIZATION", "Bearer $API_TOKEN")
            val response = chain.proceed(requestBuilder.build())
            response
        })

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
            .client(client.build()).build()
    }
}

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YTFhOTI5MDNjYThiOGIzNDBhY2EyNmQ2YTU5M2VkNiIsInN1YiI6IjY2NjBjZGM0OGE1N2FhMjUyZDYwNmE1ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.h3LWT16JOu6mWdAr8q4RcHZjVnx_iw3oLBkg4wN1o4E"