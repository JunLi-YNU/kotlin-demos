package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * Channel的多路复用
 */
class ChannelAwait {

    private fun CoroutineScope.getStrMethodFirst() = async(Dispatchers.IO) {
        delay(2000)
        "First Method"
    }

    private fun CoroutineScope.getStrMethodSecond() = async(Dispatchers.IO) {
        delay(2000)
        "Second Method"
    }

    fun selectElementBySelectOnAwait() = runBlocking {
        GlobalScope.launch {
            val firstMethod = getStrMethodFirst()
            val secondMethod = getStrMethodSecond()
            val str = select<String> {
                firstMethod.onAwait { it }
                secondMethod.onAwait { it }
            }
            println("Receive:$str")
        }.join()
    }

    fun selectElementByChannelOnReceive() = runBlocking {
        val channels = listOf(Channel<Int>(), Channel<Int>())
        GlobalScope.launch(Dispatchers.Default) {
            delay(100)
            channels[0].send(100)
        }
        GlobalScope.launch {
            delay(200)
            channels[1].send(200)
        }

        val str = select<Int?> {
            channels.forEach { channel ->
                channel.onReceive { it }
            }
        }
        println("Receive:$str")
        joinAll()
    }

}

fun main() {
    val channelAwait = ChannelAwait()
    channelAwait.selectElementBySelectOnAwait()
    channelAwait.selectElementByChannelOnReceive()
}