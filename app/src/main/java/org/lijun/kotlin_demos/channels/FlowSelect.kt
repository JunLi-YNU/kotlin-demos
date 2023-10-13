package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking

class FlowSelect {
    fun selectElementByFlowMerge() = runBlocking<Unit> {
        coroutineScope {
            listOf(::getStrMethodFirst, ::getStrMethodSecond, ::getStrMethodThird)
                .map {
                    flow {
                        emit(it.call())
                    }
                }
                .merge().collect() {
                    println("Receive:${it.await()}")
                }
        }
    }
}

fun CoroutineScope.getStrMethodFirst() = async(Dispatchers.IO) {
    delay(3000)
    "First Method"
}

fun CoroutineScope.getStrMethodSecond() = async(Dispatchers.IO) {
    delay(2000)
    "Second Method"
}

fun CoroutineScope.getStrMethodThird() = async(Dispatchers.IO) {
    delay(1000)
    "Third Method"
}

fun main() {
    val flowSelect = FlowSelect()
    flowSelect.selectElementByFlowMerge()
}