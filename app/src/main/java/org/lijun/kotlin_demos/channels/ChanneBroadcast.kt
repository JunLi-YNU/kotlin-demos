package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * 广播形式的Channel
 */
class ChannelBroadcast {
    fun broadcastChannel() = runBlocking {
//        val broadcastChannel = kotlinx.coroutines.channels.BroadcastChannel<Int>(Channel.BUFFERED)
        val channel = Channel<Int>()
        val broadcastChannel = channel.broadcast()
        GlobalScope.launch {
            List(3) {
                delay(100)
                broadcastChannel.send(it)
            }
            broadcastChannel.close()
        }
        List(3) {
            GlobalScope.launch {
                val receiveChannel = broadcastChannel.openSubscription()
                for (i in receiveChannel) {
                    println("[#$it] received: $i")
                }
            }
        }.joinAll()
    }
}

fun main() {
    val broadcastChannel = ChannelBroadcast()
    broadcastChannel.broadcastChannel()
}