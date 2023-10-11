package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * Flow流的背压处理
 */
class BackPressure {
    private fun simpleFlow() = flow<Int> {
        for (i in 1..10) {
            delay(100)
            emit(i)
            println("Emit flow element:$i")
        }
    }

    //Flow处理背压通过buffer，并发运行流中发射元素的代码
    fun dealWithBackPressureByBuffer() = runBlocking {
        simpleFlow()
            .buffer(5)
            .collect {
                delay(1000)
                println("Collect flow element:$it")
            }
        println("Done")
    }

    //Flow处理背压通过conflate，合并发射项，不对每一个值进行处理
    fun dealWithBackPressureByConflate() = runBlocking {
        simpleFlow()
            .conflate()
            .collect {
                delay(1000)
                println("Collect flow element:$it")
            }
        println("Done")
    }
    //Flow处理背压通过collectLatest，取消并重新发射最后一个值
    fun dealWithBackPressureByCollectLatest() = runBlocking {
        simpleFlow()
            .collectLatest{
                delay(1000)
                println("Collect flow element:$it")
            }
        println("Done")
    }
}

fun main() {
    val backPressure = BackPressure()
    backPressure.dealWithBackPressureByBuffer()
    backPressure.dealWithBackPressureByConflate()
    backPressure.dealWithBackPressureByCollectLatest()
}