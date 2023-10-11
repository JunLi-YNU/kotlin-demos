package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

/**
 * Flow流的完成
 */
class FlowCompletion {
    //Flow流的完成处理通过finally
    fun flowCompleteByFinally() = runBlocking {
        try {
            (1..5).asFlow()
                .onEach {
                    delay(100)
                    check(it < 3) { println("Check: It's too big!")}
                }
                .catch { println("Caught exception:$it") }
                .collect(){
                    println("Collect element:$it")
                }
        } finally {
            println("Done")
        }
    }
    //Flow流的完成处理通过onCompletion
    fun flowCompleteByOnCompletion() = runBlocking {
        flowOf("one","two","three")
            .onEach {
                delay(100)
                check(it.length < 4)
            }
            .onCompletion {
                if(it != null){
                    println("Flow complete failed!,$it")
                }
            }
            .catch { println("Caught exception:$it") }
            .collect(){
                println("Collect element:$it")
                check(it[1] == ('t'))
            }

    }
}
fun main(){
    val flowCompletion = FlowCompletion()
    flowCompletion.flowCompleteByFinally()
    flowCompletion.flowCompleteByOnCompletion()
}