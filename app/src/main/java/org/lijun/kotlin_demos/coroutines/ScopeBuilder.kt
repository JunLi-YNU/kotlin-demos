package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

class ScopeBuilder {
    fun coroutineScopeDemo() = runBlocking {
        //coroutineScope作用域当一个协程失败时，该作用域中所有协程都被取消。
        coroutineScope {
            val jobFirst = launch {
                delay(2000)
                println("JobFirst finished.")
            }
            val jobSecond = launch {
                delay(1000)
                println("JobSecond finished.")
                throw IllegalAccessException()
            }
        }
    }

    fun supervisorScopeDemo() = runBlocking {
        //supervisorScope作用域一个协程失败时，不会影响该作用域的其他协程的执行。
        supervisorScope {
            val jobFirst = launch {
                delay(2000)
                println("JobFirst finished.")
            }
            val jobSecond = launch {
                delay(1000)
                println("JobSecond finished.")
                throw IllegalAccessException()
            }
        }

    }

}

fun main() {
    val scopeBuilder = ScopeBuilder()
    scopeBuilder.supervisorScopeDemo()
    scopeBuilder.coroutineScopeDemo()
}