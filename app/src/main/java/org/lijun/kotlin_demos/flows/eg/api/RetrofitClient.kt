package org.lijun.kotlin_demos.flows.eg.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val instance: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor {
                it.proceed(it.request()).apply {
                    Log.d("json", "request:${code()}+${it.request().url()}")
                }
            }.build())
            //本地服务器不能使用localhost，需要使用本机的IP地址，因为app是运行在模拟器上的
            .baseUrl("http://192.168.91.111:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val normalUserApi: NormalUserApi by lazy {
        instance.create(NormalUserApi::class.java)
    }
}