package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel的关闭
 */
class ChannelClose {
    //Channel的关闭通过close
    fun channelClose() = runBlocking {
        val recursiveChannel = GlobalScope.produce<Int> {
            repeat(3) {
                delay(100)
                send(it)
                println("Send:$it")
            }
            close()
        }
        val consumer = GlobalScope.launch {
            for (element in recursiveChannel) {
                println("Received:$element")
            }
            println(
                """After consuming:
                | - ClosedForSend: ${recursiveChannel.isClosedForReceive}
                """
            )
        }
        consumer.join()
        val sendChannel = GlobalScope.actor<Int> {
            while (true) {
                println("Received by actor:${receive()}")
            }
        }
        val producer = GlobalScope.launch() {
            repeat(5) {
                delay(100)
                sendChannel.send(it)
                println("Send:$it")
            }
            sendChannel.close()
        }
        producer.join()
    }
}

fun main() {
    val channelClose = ChannelClose()
    channelClose.channelClose()
}