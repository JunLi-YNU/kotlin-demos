package org.lijun.kotlin_demos.flows.eg.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.lijun.kotlin_demos.flows.eg.database.AppDataBase
import org.lijun.kotlin_demos.flows.eg.database.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    fun insertUser(uid: String, userName: String, userAge: Int, userSex: Boolean) {
        viewModelScope.launch {
            AppDataBase.getInstance(getApplication())
                .userDao().insertUser(User(uid.toInt(), userName, userAge, userSex))
            Log.d("UserViewModel", "Insert user:$uid")
        }
    }

    fun getAll(): Flow<List<User>> {
        return AppDataBase.getInstance(getApplication())
            .userDao()
            .getAllUser()
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.IO)
    }
}