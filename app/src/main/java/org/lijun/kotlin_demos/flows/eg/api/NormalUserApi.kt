package org.lijun.kotlin_demos.flows.eg.api

import org.lijun.kotlin_demos.coroutines.eg.api.NormalUser
import retrofit2.http.GET
import retrofit2.http.Query

interface NormalUserApi {
    @GET("getNormalUserByNickName?")
    suspend fun searchNormalUserByNickName(
        @Query("normalUserNickName") normalUserNickName: String
    ): NormalUser
}