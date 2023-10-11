package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

/**
 * Flow流的构建方式
 */
class FlowBuilder {
    //第一种:通过flow function构建flow
    private fun builderFlowByFlowFunction() = flow<Int> {
        for (i in 1..3) {
            delay(1000)//挂起
            emit(i)
        }
    }

    fun collectFlowElements() = runBlocking {
        builderFlowByFlowFunction().collect() {
            println("Collect flow element:$it")
        }
    }

    //第二种：通过asFlow构建flow
    fun builderFlowByAsFlow() = runBlocking {
        (1..5).asFlow().filter {
            it % 2 == 0
        }.map {
            "element $it"
        }.collect() {
            println("Collect flow element:$it")
        }
    }

    //第三种：通过flowOf构建flow
    fun builderFlowByFlowOf() = runBlocking {
        flowOf("one", "two", "three")
            .onEach { delay(1000) }
            .collect() {
                println("Collect flow element:$it")
            }
    }

}

fun main() {
    val flowBuilder = FlowBuilder()
    flowBuilder.collectFlowElements()
    flowBuilder.builderFlowByAsFlow()
    flowBuilder.builderFlowByFlowOf()
}