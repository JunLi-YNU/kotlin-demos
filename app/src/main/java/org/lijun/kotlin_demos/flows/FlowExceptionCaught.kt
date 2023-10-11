package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

/**
 *Flow流的异常捕获
 */
class FlowExceptionCaught {
    private fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }

    //Flow流的下游异常捕获通过try
    fun caughtExceptionByTry() = runBlocking {

        try {
            simpleFlow().collect() {
                println(it)
                check(it < 2) { "Check failed!" }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    //Flow流的下游上游异常捕获通过catch
    fun caughtExceptionByCatch() = runBlocking {
        flowOf("one", "two", "three")
            .onEach {
                delay(100)
                if (it.length > 4) throw IndexOutOfBoundsException()
            }
            .catch {
                println("Caught $it")
                emit("four")
            }
            .flowOn(Dispatchers.IO)
            .collect() {
                println("Collect element:$it")
            }
    }
}

fun main() {
    val flowExceptionCaught = FlowExceptionCaught()
    flowExceptionCaught.caughtExceptionByTry()
    flowExceptionCaught.caughtExceptionByCatch()
}