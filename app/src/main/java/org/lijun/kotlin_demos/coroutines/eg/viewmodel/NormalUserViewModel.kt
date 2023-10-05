package org.lijun.kotlin_demos.coroutines.eg.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.lijun.kotlin_demos.coroutines.eg.api.NormalUser
import org.lijun.kotlin_demos.coroutines.eg.repository.NormalUserRepository

class NormalUserViewModel() : ViewModel() {

    val userLiveData = MutableLiveData<NormalUser>()
    private val normalUserRepository = NormalUserRepository()
    fun getNormalUser(userId: Long) {
        viewModelScope.launch {
            Log.i(TAG, "getNormalUser: "+normalUserRepository.getNormalUser(userId))
            userLiveData.value = normalUserRepository.getNormalUser(userId)
        }
    }
}