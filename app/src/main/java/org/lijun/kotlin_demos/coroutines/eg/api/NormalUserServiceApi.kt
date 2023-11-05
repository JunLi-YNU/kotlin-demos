package org.lijun.kotlin_demos.coroutines.eg.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import java.time.LocalDateTime

data class NormalUser(
    val userId: Long,
    val userName: String,
    val nickName: String,
    val suerType: String,
    val email: String,
    val phoneNumber: String,
    val sex: String,
    val avatar: String,
    val password: String,
    val status: String,
    val delFlag: String,
    val loginIp: String,
    val loginDate: String,
    val createBy: String,
    val createTime: LocalDateTime,
    val updateBy: String,
    val updateTime: String,
    val remark: String
)

interface NormalUserServiceApi {
    @GET("getNormalUserById?")
    suspend fun getNormalUser(@Query("normalUserId") userId: Long): NormalUser
}

val normalUserServiceApi: NormalUserServiceApi by lazy {
    val retrofit = retrofit2.Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor {
            it.proceed(it.request()).apply {
                Log.d("json", "request:${code()}")
            }
        }.build())
        //本地服务器不能使用localhost，需要使用本机的IP地址，因为app是运行在模拟器上的
        .baseUrl("http://192.168.91.111:8088/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    retrofit.create(NormalUserServiceApi::class.java)

}