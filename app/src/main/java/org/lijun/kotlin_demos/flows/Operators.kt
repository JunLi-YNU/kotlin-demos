package org.lijun.kotlin_demos.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

/**
 * Flow流中操作符的使用
 */
class Operators {
    //操作Flow流中的元素通过Map操作符
    fun operatorFlowByMap() = runBlocking {
        (1..5).asFlow().map { "Operator element by map:$it" }.collect() {
            println(it)
        }
    }

    //操作Flow流中元素通过transform操作符
    fun operatorFlowByTransform() = runBlocking {
        (1..5).asFlow().transform {
            emit("Operator element by transform:$it")
            emit("Operator element by transform emit:$it")
        }.collect() {
            println(it)
        }
    }

    //操作Flow流中的元素通过限长操作符take
    fun operatorFlowByTake() = runBlocking {
        flowOf("One", "two", "three", "four").onEach { delay(1000) }.take(3).collect() {
            println("Take flow element:$it")
        }
    }

    //其他操作符
    fun operatorFlowByReduceFoldToListToSetFirst() = runBlocking {
        val flow = (1..5).asFlow()
        val reduce = flow.map { it * it }.reduce() { a, b -> a + b }
        println("Reduce:${reduce}")
        val fold = flow.fold(2) { a, b -> a + b }
        println("Fold:$fold")
        val list = flow.toList()
        println("List:$list")
        val set = flow.toSet()
        println("Set:$set")
        val first = flow.first()
        println("First:$first")
    }

    //组合Flow流通过zip
    fun operatorFlowByZip() = runBlocking {
        val num = (1..3).asFlow().onEach { delay(100) }
        val str = flowOf("one", "two", "three").onEach { delay(300) }
        val startTime = System.currentTimeMillis()
        num.zip(str) { a, b -> "$a->$b" }.collect() {
            println("Collect flow:$it time:${System.currentTimeMillis() - startTime}")
        }
    }

    private fun simpleFlow(i: Int) = flow<String> {
        emit("$i :First")
        delay(400)
        emit("$i :Second")

    }

    //展平Flow流通过flatMapConcat
    fun operatorFlowByFlatMapConcat() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(200) }
            .flatMapConcat { simpleFlow(it) }
            .collect() {
            println("Collect flow by flatMapConcat:$it time:${System.currentTimeMillis() - startTime}")
        }
    }

    //合并Flow流通过flatMapMerge
    fun operatorFlowByFlatMapMerge() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(200) }
            .flatMapMerge { simpleFlow(it) }
            .collect() {
            println("Collect flow by flatMapMerge:$it time:${System.currentTimeMillis() - startTime}")
        }
    }

    //合并Flow流通过flatMapLatest
    fun operatorFlowByFlatMapLatest() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(200) }
            .flatMapLatest { simpleFlow(it) }
            .collect() {
                println("Collect flow by flatMapLatest:$it time:${System.currentTimeMillis() - startTime}")
            }
    }
}

fun main() {
    val operator = Operators()
    operator.operatorFlowByFlatMapLatest()
    operator.operatorFlowByFlatMapMerge()
    operator.operatorFlowByFlatMapConcat()
    operator.operatorFlowByMap()
    operator.operatorFlowByTransform()
    operator.operatorFlowByTake()
    operator.operatorFlowByReduceFoldToListToSetFirst()
    operator.operatorFlowByZip()

}