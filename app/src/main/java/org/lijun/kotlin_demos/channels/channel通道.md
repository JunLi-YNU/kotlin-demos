# Channel热流
> 一个并发的安全队列，用来连接协程，实现不同协程的通信，它是一种热流。
## Channel的特性
>1.channel的队列当receive不能快速处理任务时，send会被挂起。
>2.channel的关闭一般由上游send方进行。
>3.channel是并发安全的通道。

## Channel的创建
**创建一个channel通道通过Channel**
```kotlin
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
```
**创建一个channel通道通过produce**
```kotlin
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
```
**创建一个channel通道通过actor**
```kotlin
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
```
## Channel的迭代##
**迭代Channel通过iterator**
```kotlin
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
```
**迭代Channel通过for in**
```kotlin
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
```
## Channel的取消
**Channel的关闭通过close**
```kotlin
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
```
## 广播形式的Channel
>1.Broadcast已经被弃用。