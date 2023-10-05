package org.lijun.kotlin_demos.coroutines.eg.repository

import org.lijun.kotlin_demos.coroutines.eg.api.NormalUser
import org.lijun.kotlin_demos.coroutines.eg.api.normalUserServiceApi
import retrofit2.Call

class NormalUserRepository {
    suspend fun getNormalUser(userId: Long): NormalUser {
        return normalUserServiceApi.getNormalUser(userId)
    }
}