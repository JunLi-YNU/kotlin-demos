package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * 支持多路复用的事件
 */
class SelectClause {
    //多路复用：无返回值SelectClause0
    fun selectClauseFirst() = runBlocking {

        val jobFirst = GlobalScope.launch {
            delay(100)
            println("Job first")
        }
        val jobSecond = GlobalScope.launch {
            delay(50)
            println("Job second")
        }
        select {
            jobFirst.onJoin { println("Job first on join.") }
            jobSecond.onJoin { println("Job second on join.") }
        }
        delay(200)
    }

    //多路复用：有返回值SelectClause1
    fun selectClauseSecond() = runBlocking {

    }

    //多路复用：有返回值，有回调函数SelectClause2
    fun selectClauseThird() = runBlocking {
        val channels = listOf(Channel<Int>(), Channel<Int>())
        //kotlin为了能够和C代码互操作，已经不再指定变量的特定堆地址
        println(channels)

        launch(Dispatchers.IO) {
            select<Unit?> {
                launch {
                    delay(100)
                    channels[0].onSend(100) {
                        println("Send on:$it")
                    }
                }
                launch {
                    delay(50)
                    channels[1].onSend(50) {
                        println("Send on:$it")
                    }
                }
            }

        }
        GlobalScope.launch {
            delay(200)
            println("received:${channels[0].receive()}")
        }
        GlobalScope.launch {
            delay(200)
            println("received:${channels[1].receive()}")
        }
    }
}

fun main() {
    val selectClause = SelectClause()
    selectClause.selectClauseFirst()
    selectClause.selectClauseSecond()
    selectClause.selectClauseThird()
}