package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScopeCancel {
    fun scopeCancel() = runBlocking {
        //CoroutineScope作用域是独立的，并没有继承runBlocking
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            delay(1000)
            println("JobFirst finished.")
        }
        scope.launch {
            delay(1000)
            println("JonSecond finished.")
        }
        delay(500)
        //协程没有执行完成前，取消作用域就能取消协程
        scope.cancel()
        //由于runBlocking的作用域与CoroutineScope不同，所以runBlocking不会等待CoroutineScope中的协程执行完成
        delay(2000)
    }

    fun scopeCancelBrotherCoroutine() = runBlocking {
        //CoroutineScope作用域是独立的，并没有继承runBlocking
        val scope = CoroutineScope(Dispatchers.Default)
        val jobFirst = scope.launch {
            delay(1000)
            println("JobFirst finished.")
        }
        val jobSecond = scope.launch {
            delay(1000)
            println("JonSecond finished.")
        }
        delay(500)
        //协程没有执行完成前，取消作用域就能取消协程
        jobFirst.cancel()
        //由于runBlocking的作用域与CoroutineScope不同，所以runBlocking不会等待CoroutineScope中的协程执行完成
        delay(2000)
    }
}

fun main() {
    val scopeCancel = ScopeCancel()
    scopeCancel.scopeCancel()
    scopeCancel.scopeCancelBrotherCoroutine()
}