package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 协程的结构化并发
 */
class CombineAsync {
    fun combineAsync() = runBlocking {
        val executedTime = measureTimeMillis {
            val first = async { doCoroutine() }
            val second = async { doCoroutine() }
            println("The result: ${first.await() + second.await()}")
        }
        println("Completed in $executedTime ms.")
    }

    fun wrongUseCombineAsync() = runBlocking {
        val executedTime = measureTimeMillis {
            //错误的使用方法，这时的协程是同步的
            val first = async { doCoroutine() }.await()
            val second = async { doCoroutine() }.await()
            println("The result: ${first + second}")
        }
        println("Completed in $executedTime ms.")
    }

    private suspend fun doCoroutine(): Int {
        delay(1000)
        return 10
    }
}

fun main() {
    var combineAsync: CombineAsync = CombineAsync()
    combineAsync.combineAsync()
    combineAsync.wrongUseCombineAsync()
}