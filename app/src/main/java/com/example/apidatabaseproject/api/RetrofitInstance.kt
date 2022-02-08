package com.example.apidatabaseproject.api

import com.example.apidatabaseproject.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY) //to see the body of the actual response
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }

        val api by lazy{
            retrofit.create(NewsAPI::class.java)
        }
    }
}