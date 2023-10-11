package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

/**
 * Flow流的上下文
 */
class FlowContext {
    //流的发射和收集总在同一空间进行
    private fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            delay(1000)
            emit(i)
            println("emit flow element:$i Thread:${Thread.currentThread().name}")
        }
    }

    fun collectFowlElement() = runBlocking {
        simpleFlow().collect() {
            println("Collect flow element:$it Thread:${Thread.currentThread().name}")
        }
    }

    //flowOn改变流的发射空间
    private fun changeFlowContext() = flow<Int> {
        for (i in 1..3) {
            delay(1000)
            emit(i)
            println("emit flow element:$i Thread:${Thread.currentThread().name}")
        }
    }.flowOn(Dispatchers.IO)

    fun collectFlowElementByFlowOn() = runBlocking {
        changeFlowContext().collect() {
            println("Collect flow element:$it Thread:${Thread.currentThread().name}")
        }
    }

    //指定流的收集协程
    private fun events() = (1..3).asFlow()
        .onEach { delay(1000) }
        .flowOn(Dispatchers.Default)

    fun collectFlowElementBbyLaunchIn() = runBlocking {
        events()
            .onEach { println("Collect flow element:$it Thread:${Thread.currentThread().name}") }
            .launchIn(CoroutineScope( Dispatchers.IO))
            .join()
    }
}

fun main() {
    val flowContext = FlowContext()
    flowContext.collectFowlElement()
    flowContext.collectFlowElementByFlowOn()
    flowContext.collectFlowElementBbyLaunchIn()
}