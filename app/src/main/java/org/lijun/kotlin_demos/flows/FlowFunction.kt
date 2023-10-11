package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Flow流的功能
 */
class FlowFunction {

    private fun simpleList(): List<Int> = listOf(1, 2, 3)

    private fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(1000)//阻塞
//            delay(1000)//挂起函数，不能使用
            yield(i)//只允许Sequence自己的挂起函数
        }
    }

    fun multipleValuesByList() {
        println("Print multiple Values by list:")
        simpleList().forEach { value ->
            println(value)
        }
    }

    //同步的一次一个的返回多个值
    fun multipleValuesBySequence() {
        println("Print multiple Values by sequence:")
        simpleSequence().forEach { value ->
            println(value)
        }
    }

    private fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            delay(1000)//挂起
            emit(i)
        }
    }

    //异步的一次一个的返回多个值
    fun multipleValuesByFlow() = runBlocking {
        println("Print multiple Values by flow:")
        launch {
            for (i in 1..3) {
                delay(1000)
                println("I'm not blocked $i")
            }
        }
        simpleFlow().collect() { value ->
            println(value)
        }
        //flow流是一种冷启动流
        println("Collect flow again：")
        simpleFlow().collect(){value->
            println(value)
        }
    }
}

fun main() {
    val flowFunction = FlowFunction()
    flowFunction.multipleValuesByList()
    flowFunction.multipleValuesBySequence()
    flowFunction.multipleValuesByFlow()
}