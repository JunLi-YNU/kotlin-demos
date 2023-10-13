package org.lijun.kotlin_demos.channels

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel通道的构建
 */
class ChannelBuilder {
    //创建一个channel通道通过Channel
    fun channelBuilderByChannel() = runBlocking {
        var channel = Channel<Int>()
        val producer = GlobalScope.launch {
            var i = 0
            while (i < 3) {
                delay(1000)
                channel.send(++i)
                println("Send:$i")
            }
            channel.close()
        }
        val consumer = GlobalScope.launch {

            try {
                while (true) {
                    delay(2000)
                    val element = channel.receive()
                    println("Receive:$element")
                }
            } catch (e: Exception) {
                println("Caught:$e")
            }
        }
        joinAll(producer, consumer)
    }

    //创建一个channel通道通过produce
    fun channelBuilderByProducer() = runBlocking {
        val receiveChannel = GlobalScope.produce<Int> {
            repeat(10) {
                delay(100)
                send(it)
                println("Send by producer:$it")
            }
            close()
        }
        val consumer = GlobalScope.launch() {
            for (it in receiveChannel) {
                println("Received:$it")
            }
        }
        consumer.join()
    }

    //创建一个channel通道通过actor
    fun channelBuilderByPActor() = runBlocking {
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
    val channelBuilder = ChannelBuilder()
    channelBuilder.channelBuilderByChannel()
    channelBuilder.channelBuilderByProducer()
    channelBuilder.channelBuilderByPActor()
}