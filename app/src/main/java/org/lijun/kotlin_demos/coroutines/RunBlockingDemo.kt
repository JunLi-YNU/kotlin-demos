package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


fun main() {
    val runBlocking = runBlocking(Dispatchers.IO) {
        println(Thread.currentThread().name)
        "This a runBlocking"
    }
    println(runBlocking)
    println(Thread.currentThread().name)
}