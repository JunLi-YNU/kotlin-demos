package org.lijun.kotlin_demos.coroutines.construction

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 协程的上下文CoroutineContext
 */
class Context {
    //协程上下文的定义
    fun coroutineContextDemo() = runBlocking {
        launch(Dispatchers.Default + CoroutineName("CoroutineContextDemoName")) {
            println("I'm working in thread ${Thread.currentThread().name} coroutineName:${coroutineContext[CoroutineName]}}")
        }
    }

    //协程上下文的继承关系
    fun coroutineContextExtend() = runBlocking {
        val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("Scope"))
        val job = scope.launch {
            //该协程的父级是协程作用域scope
            println("Current coroutine Job:${coroutineContext[Job]} Thread:${Thread.currentThread().name} CoroutineName:${coroutineContext[CoroutineName]}")
            async {
                //该协程的父级是launch
                println("Current coroutine Job:${coroutineContext[Job]} Thread:${Thread.currentThread().name} CoroutineName:${coroutineContext[CoroutineName]}")
                "async"
            }.await()
        }
        job.join()
    }

    //协程的异常
    fun coroutineContextException() = runBlocking {
        val coroutineException = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.Main + coroutineException)
        val job = scope.launch {
            println("Coroutine Exception:${coroutineContext}")
        }
        job.join()
    }
}

fun main() {
    val context = Context()
    context.coroutineContextDemo()
    context.coroutineContextExtend()
    context.coroutineContextException()
}