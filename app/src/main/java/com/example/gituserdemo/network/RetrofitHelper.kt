package com.example.gituserdemo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    var BASE_URL = "https://api.github.com/"

    companion object{
        val instance = RetrofitHelper()
    }

    fun getRetrofit() : ApiInterface {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient =
            OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
        httpClient.retryOnConnectionFailure(true)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build().create(ApiInterface::class.java)

    }


    fun getCreate(retrofit : Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}