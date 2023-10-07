package org.lijun.kotlin_demos.coroutines.construction

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Exception

/**
 * 协程的取消
 */
class Cancellation {

    //取消协程的异常
    fun cancellationException() = runBlocking {
        val jobFirst = GlobalScope.launch {
            try {
                delay(1000)
                println("JobFirst finished.")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        delay(100)
        //取消协程会抛出一个异常，但是默认被静默处理
        jobFirst.cancelAndJoin()
//        jobFirst.cancel()
//        jobFirst.join()
    }

    //isActive 取消CPU密集型任务(CPU密集型任务一般情况下是不能被取消的)
    fun cancelCPUTaskByIsActive() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 && isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("Job: I'm sleeping ${i++}")
                    nextPrintTime += 500
                }
            }
        }
        delay(1000)
        println("Main: I'm tired of waiting.")
        //这里是无法取消该携程的
        job.cancelAndJoin()
        println("Main: Now I'm can quit.")
    }

    //ensureActive() 取消CPU密集型任务(CPU密集型任务一般情况下是不能被取消的)
    fun cancelCPUTaskByEnsureActive() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                ensureActive()
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("Job: I'm sleeping ${i++}")
                    nextPrintTime += 500
                }
            }
        }
        delay(1000)
        println("Main: I'm tired of waiting.")
        //这里是无法取消该携程的
        job.cancelAndJoin()
        println("Main: Now I'm can quit.")
    }

    //yield() 取消CPU密集型任务(CPU密集型任务一般情况下是不能被取消的)
    fun cancelCPUTaskByYield() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                yield()
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("Job: I'm sleeping ${i++}")
                    nextPrintTime += 500
                }
            }
        }
        delay(1000)
        println("Main: I'm tired of waiting.")
        //这里是无法取消该携程的
        job.cancelAndJoin()
        println("Main: Now I'm can quit.")
    }

    //finally中取消协程并释放资源
    fun cancelAndReleaseResources() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("Job: I'm sleeping $i")
                    delay(500L)
                }
            } finally {
                //在finally中释放资源
                println("Job: I'm running finally.")
            }
        }
        delay(1000)
        println("Main: I'm tired of waiting.")
        //这里是无法取消该携程的
        job.cancelAndJoin()
        println("Main: Now I'm can quit.")
    }

    //use函数中不需要释放资源，底层已经实现了资源的释放
    fun cancelAndReleaseResourcesByUse() = runBlocking {
        var br =
            BufferedReader(FileReader("/Users/junli/AndroidStudioProjects/my_workspace/kotlin_demos/app/src/main/java/org/lijun/kotlin_demos/coroutines/construction/协程的构建方法.md"))
        with(br) {
            var line: String?
            try {
                while (true) {
                    line = readLine() ?: break;
                    println(line)
                }
            } finally {
                //取消
                close()
            }
        }

        BufferedReader(FileReader("/Users/junli/AndroidStudioProjects/my_workspace/kotlin_demos/app/src/main/java/org/lijun/kotlin_demos/coroutines/construction/协程的构建方法.md"))
            .use {
                var line: String?
                while (true) {
                    line = it.readLine() ?: break;
                    println(line)
                }
            }
    }

    //withContext(NonCancelable)将协程任务设置为不能被取消
    fun cancelWithNonCancelable() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("Job: I'm sleeping $i")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    //在finally中释放资源
                    println("Job: I'm running finally.")
                    delay(1000)
                    println("Job: And I've just delayed for 1 sec because I'm non-cancelable.")
                }
            }
        }
        delay(1000)
        println("Main: I'm tired of waiting.")
        //这里是无法取消该携程的
        job.cancelAndJoin()
        println("Main: Now I'm can quit.")
    }

    //超时任务的取消
    fun dealWithTimeoutReturnNull() = runBlocking {
        val result = withTimeoutOrNull(1500) {
            repeat(1000) { i ->
                println("Job: I'm sleeping $i")
                delay(500L)
            }
            "Done"
        } ?: "TimeOut"
        println("Result is : $result")
    }
}

fun main() {
    val cancellation = Cancellation()
    cancellation.cancellationException()
    cancellation.cancelCPUTaskByIsActive()
    cancellation.cancelCPUTaskByEnsureActive()
    cancellation.cancelCPUTaskByYield()
    cancellation.cancelAndReleaseResources()
    cancellation.cancelAndReleaseResourcesByUse()
    cancellation.cancelWithNonCancelable()
    cancellation.dealWithTimeoutReturnNull()

}