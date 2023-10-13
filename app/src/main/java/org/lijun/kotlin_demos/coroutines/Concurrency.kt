package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 协程的并发安全
 */
class Concurrency {
    //coroutine并发安全通过Atomic
    fun concurrencyByAtomic() = runBlocking {
        var count = AtomicInteger(0)
        List(1000) {
            GlobalScope.launch {
                count.addAndGet(1)
            }
        }.joinAll()
        println("Atomic:$count")
    }

    //coroutine并发安全通过Mutex
    fun concurrencyByMutex() = runBlocking {
        var count = 0
        val mutex = Mutex()
        List(1000) {
            GlobalScope.launch {
                mutex.withLock {
                    count++
                }
            }
        }.joinAll()
        println("Mutex:$count")
    }

    //coroutine并发安全通过Semaphore
    fun concurrencyBySemaphore() = runBlocking {
        var count = 0
        val semaphore = Semaphore(1)
        List(1000) {
            GlobalScope.launch {
                semaphore.withPermit {
                    count++
                }
            }
        }.joinAll()
        println("Atomic:$count")
    }
}

fun main() {
    val concurrency = Concurrency()
    concurrency.concurrencyByAtomic()
    concurrency.concurrencyByMutex()
    concurrency.concurrencyBySemaphore()
}