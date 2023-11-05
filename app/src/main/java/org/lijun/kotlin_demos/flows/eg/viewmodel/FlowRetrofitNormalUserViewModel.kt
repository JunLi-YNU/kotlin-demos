package org.lijun.kotlin_demos.flows.eg.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.lijun.kotlin_demos.coroutines.eg.api.NormalUser
import org.lijun.kotlin_demos.flows.eg.api.RetrofitClient

class FlowRetrofitNormalUserViewModel(application: Application) : AndroidViewModel(application) {
    val normalUserList = MutableLiveData<List<NormalUser>>()
    fun searchNormalUserByNickName(normalUserNickname: String) {
        viewModelScope.launch {
            flow {
                val list = mutableListOf<NormalUser>()
                list.add(RetrofitClient.normalUserApi.searchNormalUserByNickName(normalUserNickname))
                emit(list)
            }.flowOn(Dispatchers.IO)
                .catch {
                    Log.d("LiJun", "发生错误")
                    it.printStackTrace()
                }
                .collect {
                    normalUserList.setValue(it)
                }
        }
    }
}