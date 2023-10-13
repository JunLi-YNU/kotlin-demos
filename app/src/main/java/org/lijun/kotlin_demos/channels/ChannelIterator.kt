package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel通道元素的迭代
 */
class ChannelIterator {

    //迭代Channel通过iterator
    fun iteratorChannelElementByIterator() = runBlocking {
        val channel = Channel<Int>(Channel.UNLIMITED)
        val producer = GlobalScope.launch {
            for (i in 1..5) {
                channel.send(i)
                println("Send:$i")
            }
            delay(6000)
            channel.close()
        }
        val consumer = GlobalScope.launch() {
            val iterator = channel.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                println("Receive by iterator:$element")
                delay(1000)
            }
        }
        joinAll(producer, consumer)
    }

    //迭代Channel通过for in
    fun iteratorChannelElementByForIn() = runBlocking {
        val channel = Channel<Int>(Channel.UNLIMITED)
        val producer = GlobalScope.launch {
            for (i in 1..5) {
                channel.send(i)
                println("Send:$i")
            }
            channel.close()
        }
        val consumer = GlobalScope.launch() {
            for (element in channel) {
                println("Receive by for in:$element")
                delay(1000)
            }
        }
        joinAll(producer, consumer)
    }
}

fun main() {
    val channelIterator = ChannelIterator()
    channelIterator.iteratorChannelElementByIterator()
    channelIterator.iteratorChannelElementByForIn()
}