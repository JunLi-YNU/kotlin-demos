package org.lijun.kotlin_demos.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StartMode {

    //1.CoroutineStart.DEFAULT
    fun coroutinesStartModeDefault() = runBlocking {
        val job = launch(start = CoroutineStart.DEFAULT) {
            println("Job start.")
            delay(5000)
            println("Job finished.")
        }
        delay(1)
        //立即执行，协程调度前被取消，则立即进入取消响应状态
        job.cancel()
    }

    //2.CoroutineStart.ATOMIC
    fun coroutinesStartModeAtomic() = runBlocking {
        val job = launch(start = CoroutineStart.ATOMIC) {
            println("Job start.")
            delay(5000)
            println("Job first hang up finished.")
            delay(1000)
            println("Job second hang up finished.")
        }
        delay(1)
        //立即执行，协程调度执行到第一个挂起点的时候不响应取消
        job.cancel()
    }

    //3.CoroutineStart.LAZY
    fun coroutinesStartModeLazy() = runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            println("Job start.")
            delay(5000)
            println("Job first hang up finished.")
            delay(1000)

        }
        delay(1000)
        println("job not yet call")
        job.join()
        //等待主动调用协程，（start，join，await函数）协程调度前被取消，则进入异常状态
        job.cancel()
    }

    //4.CoroutineStart.UnDISPATCHED
    fun coroutinesStartModeUnDispatched() = runBlocking {
        val job = launch(start = CoroutineStart.UNDISPATCHED, context = Dispatchers.IO) {
            println("ThreadName:" + Thread.currentThread().name)
            delay(5000)
            println("ThreadName:" + Thread.currentThread().name)
            println("Job finished.")
        }
        delay(1000)
        //构建后不是立即调度而是在当前线程中立即执行。协程调度前被取消，则立即进入取消响应状态
        job.cancel()
    }
}

fun main() {
    val startMode = StartMode()
    startMode.coroutinesStartModeDefault()
    startMode.coroutinesStartModeAtomic()
    startMode.coroutinesStartModeLazy()
    startMode.coroutinesStartModeUnDispatched()
}