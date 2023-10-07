package org.lijun.kotlin_demos

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d("CoroutineException","UnHandled coroutine exception $exception")
    }
}