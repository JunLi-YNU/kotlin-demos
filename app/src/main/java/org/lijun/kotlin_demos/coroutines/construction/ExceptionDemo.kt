package org.lijun.kotlin_demos.coroutines.construction

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.lang.AssertionError
import java.lang.IllegalStateException
import java.util.IllegalFormatCodePointException

class ExceptionDemo {
    //根协程的异常需要主动捕获
    fun exceptionPropagation() = runBlocking {
        val job = GlobalScope.launch {
            try {
                throw IndexOutOfBoundsException()
            } catch (e: Exception) {
                println("Caught IndexOutOfBoundsException.")
            }
        }
        job.join()
        val deferred = GlobalScope.async {
            throw ArithmeticException()
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("Caught ArithmeticException")
        }
    }

    //非根协程异常会立即抛出
    fun exceptionNonPropagation() = runBlocking {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            async {
                throw IllegalAccessException()
            }
        }
        job.join()
    }

    //supervisorJob子级协程抛出异常时，不会取消父级协程的其他任务
    fun supervisorJobExceptionHandler() = runBlocking {
        val scope = CoroutineScope(SupervisorJob())
        val jobFirst = scope.launch {
            delay(100)
            println("Job first start.")
            throw IllegalStateException()
        }
        //当jobFirst抛出异常时，该协程不会被取消执行
        val jobSecond = scope.launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Job second finished.")
            }
        }
        joinAll(jobFirst, jobSecond)
    }

    //supervisorScope子级协程抛出异常时，不会取消父级协程的其他任务
    fun supervisorScopeExceptionHandler() = runBlocking {
        supervisorScope {
            val jobFirst = launch {
                delay(100)
                println("Job first start.")
                throw IllegalStateException()
            }
            //当jobFirst抛出异常时，该协程不会被取消执行
            val jobSecond = launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Job second finished.")
                }
            }
        }
    }

    //launch抛出的异常能被捕获，async不能
    fun launchAndAsyncExceptionHandler() = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val job = GlobalScope.launch(exceptionHandler) {
            throw AssertionError()
        }
        val deferred = GlobalScope.async(exceptionHandler) {
            throw ArithmeticException()
            "finished"
        }
        job.join()
        deferred.await()
    }

    //CoroutineExceptionHandler安装位置不对捕获不到异常
    fun launchNotCaughtExceptionHandler() = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        //异常处理器应该安装在外部
        val job = GlobalScope.launch() {
            launch(exceptionHandler) {
                throw AssertionError()
            }
        }
        job.join()
    }

    //不能被取消的的任务在异常被捕获之前执行
    fun exceptionHandlerAfterNonCancel() = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val job = GlobalScope.launch(exceptionHandler) {
            launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    withContext(NonCancellable) {
                        println("Execute non cancel")
                        delay(100)
                    }
                }
            }
            launch {
                println("Throw exception")
                throw IllegalAccessException()
            }
        }
        job.join()
    }

    //多个异常的获取
    fun manyExceptionCaught() = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler{_,exception->
            println("Caught $exception ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(exceptionHandler){
            launch {
                try{
                    delay(Long.MAX_VALUE)
                }finally {
                    throw IllegalArgumentException()
                }
            }
            launch {
                try{
                    delay(Long.MAX_VALUE)
                }finally {
                    throw IllegalAccessException()
                }
            }
            launch {
                throw IndexOutOfBoundsException()
            }
        }
        job.join()
    }
}

fun main() {
    val exceptionDemo = ExceptionDemo()
//    exceptionDemo.exceptionPropagation()
//    exceptionDemo.exceptionNonPropagation()
//    exceptionDemo.supervisorJobExceptionHandler()
//    exceptionDemo.supervisorScopeExceptionHandler()
//    exceptionDemo.launchAndAsyncExceptionHandler()
//    exceptionDemo.launchNotCaughtExceptionHandler()
//    exceptionDemo.exceptionHandlerAfterNonCancel()
    exceptionDemo.manyExceptionCaught()
}