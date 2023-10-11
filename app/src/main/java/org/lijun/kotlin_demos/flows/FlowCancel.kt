package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class FlowCancel {
    private fun simpleFlow() = flow<Int> {
        for (i in 1..5) {
            delay(1000)
            emit(i)
            println("Emitting element:$i")
        }
    }

    //取消Flow通过withTimeout
    fun cancelFlowByWithTimeOut() = runBlocking {
        withTimeoutOrNull(2500) {
            simpleFlow().collect() {
                println("Collect flow element:$it")
            }
        }
        println("Done")
    }

    //取消Flow通过cancel()
    fun cancelFlowByCancel() = runBlocking {
        simpleFlow().collect(){
            println("Collect flow element:$it")
            if(it == 3) cancel()
        }
        println("Done")
    }
    //取消繁忙的Flow通过cancelable属性
    fun cancelFlowByCancelable() = runBlocking {
        (1..5).asFlow().cancellable()
            .collect(){
                println("Collect flow element:$it")
                if(it == 3) cancel()
            }
        println("Done")
    }
}

fun main() {
    val flowCancel = FlowCancel()
    flowCancel.cancelFlowByWithTimeOut()
//    flowCancel.cancelFlowByCancel()
    flowCancel.cancelFlowByCancelable()
}