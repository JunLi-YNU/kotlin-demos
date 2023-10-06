package org.lijun.kotlin_demos.coroutines.construction

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 协程的创建方式
 */
fun main() {
    val runBlocking = runBlocking(Dispatchers.IO) {
        println(Thread.currentThread().name)
        "This a runBlocking"
    }
    println(runBlocking)
    println(Thread.currentThread().name)
    coroutinesBuilder()
    coroutinesJoin()
}
//启动一个协程的两种方式
fun coroutinesBuilder() = runBlocking {
    //1.launch 不带返回值
    val jobFirst = launch {
        delay(2000)
        println("First coroutine executed!")
    }
    //2.async 协带一个返回值
    val jobSecond = async {
        delay(2000)
        println("Second coroutine executed!")
        "Job Second Result"
    }
    println(jobSecond.await())
}

//协程成的挂起
fun coroutinesJoin() = runBlocking {
    val jobFirstLaunch = launch {
        delay(2000)
        println("First coroutine executed!")
    }
    //launch函数的挂起
    jobFirstLaunch.join()
    val jobSecondLaunch = launch {
        delay(2000)
        println("Second coroutine executed!")
    }
    val jobThirdLaunch = launch {
        delay(2000)
        println("Third coroutine executed!")
    }

    val jobFirstAsync = async {
        delay(2000)
        println("First coroutine executed!")
    }
    //async函数的挂起
    jobFirstAsync.await()
    val jobSecond = async {
        delay(2000)
        println("Second coroutine executed!")
    }
    val jobThirdAsync = async {
        delay(2000)
        println("Third coroutine executed!")
    }
}