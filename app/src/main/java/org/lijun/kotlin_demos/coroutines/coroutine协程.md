# 协程

## 协程的构建方式
**GlobalScope**
> 一个App级别的协程，如果任务没有完成在App被关闭前都不会被关闭。

**runBlocking**
> 把主线程变成一个协程,它会阻塞主线程main等待协程执行完毕。

### 协程的构建器
> **launch 和 async构建器都可以用来启动一个协程**
> launch:返回一个job不附带任何结果值。
> async:返回一个Deferred，Deferred也是一个job，可以使用.await()在一个延期的值上得到它的结果。

#### 结构化并发
> 多个协程共同执行
```kotlin
    fun combineAsync() = runBlocking {
        val executedTime = measureTimeMillis {
            val first = async { doCoroutine() }
            val second = async { doCoroutine() }
            println("The result: ${first.await() + second.await()}")
        }
        println("Completed in $executedTime ms.")
    }
    fun wrongUseCombineAsync() = runBlocking {
        val executedTime = measureTimeMillis {
            //错误的使用方法，这时的协程是同步的
            val first = async{ doCoroutine()}.await()
            val second = async{ doCoroutine()}.await()
            println("The result: ${first + second}")
        }
        println("Completed in $executedTime ms.")
    }

    private suspend fun doCoroutine(): Int {
        delay(1000)
        return 10
    }
```
### 协程的启动模式
>1.CoroutineStart.DEFAULT : 构建协程后立即开始调度，在调度前如果被取消，则立即进入取消响应状态。
>2.CoroutineStart.ATOMIC : 构建协程后立即开始调度，执行到第一个挂起点之前不响应取消。
>3.CoroutineStart.LAZY : 只有在协程被主动调用时才开始调度，在调度前如果被取消，则立即进入异常响应状态。
>4.CoroutineStart.UNDISPATCHED :协程构建后立即在当前线程立即执行。

### 协程的作用域
>**coroutineScope:**作用域当一个协程失败时，该作用域中所有协程都被取消。
>**supervisorScope:**作用域一个协程失败时，不会影响该作用域的其他协程的执行。

### Job的生命周期
>1.每通过launch或者async创建一个协程，都会返回一个Job实例，该实例是协程的唯一标识，并且负责管理协程的生命周期。
>2.一个Job包含一系列的状态：New新创建，Active活跃，Completing完成中，Completed已完成，Canceling取消中，Cancelled已取消。
>3.Job的状态是无法直接访问的，但是可以访问Job的属性：isActive，isCancelled，isCompleted.

### 协程的取消
#### 协程的作用域的取消
```kotlin
    fun scopeCancel() = runBlocking {
        //CoroutineScope作用域是独立的，并没有继承runBlocking
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            delay(1000)
            println("JobFirst finished.")
        }
        scope.launch {
            delay(1000)
            println("JonSecond finished.")
        }
        delay(500)
        //协程没有执行完成前，取消作用域就能取消协程
        scope.cancel()
        //由于runBlocking的作用域与CoroutineScope不同，所以runBlocking不会等待CoroutineScope中的协程执行完成
        delay(2000)
    }
```
#### 协程CPU密集型任务的取消
>1.isActive是一个可以被使用在CoroutinesScope中的扩展属性，检查Job是否处于活跃状态。
>2.ensureActive(),如果Job处于非活跃状态，这个方法会立即抛出异常。
>3.yield()函数会检查所在协程的状态，如果已经取消，则会抛出CancellationException予以响应，此外它还会让出执行权，给其他协程提供执行机会。


### 协程的异常处理
#### 协程的上下文
> CoroutineContext是一组用于定义携程行为的元素：
> 1.Job: 控制协程的生命周期。
> 2.CoroutineDispatcher: 向合适的线程分发任务。
> 3.CoroutineName: 定义协程的名称，常用于调试。
> 4.CoroutineExceptionHandler: 处理未被捕捉的异常。

##### 协程上下文的继承
>**协程的上下文= 默认值 + 协程的继承的元素 + 参数（覆盖父类的内容）
>1.对于新创建的协程，它的CoroutineContext会包含一个全新的Job，帮助控制该协程的生命周期。
>2.对于新创建的协程，除了Job外的元素它会继承其父携程或者定义该协程的协程作用域。

#### 协程的异常
##### 异常的传播
>1.自动传播异常(launch和actor)：异常发生的第一时间被抛出。
>2.向用户暴露异常(async和produce): 依赖用户的最终消费，例如通过await和receive。
>3.非根协程产生的异常会立即抛出

##### 异常的传播特性
>**协程的异常发生会传播给它的父级**
>1.父级取消自己的子级任务
>2.取消它自己的任务
>3.将异常继续传播给它的父级(propagate up)

##### SupervisorJob 和 supervisorScope
>1.SupervisorJob: 定义在父级协程上下文中，当子级发生异常时父级不会取消所有子级任务的执行。
>2.supervisorScope: 定义在父级协程作用域中，当子级发生异常时父级不会取消所有子级任务的执行。

#### 协程异常的处理
**使用CoroutineExceptionHandler对异常进行捕获**
>**当异常满足以下条件时，异常就会被捕获**
>**时机:**异常被自动抛出异常的协程所抛出（launch开启的协程，async开启的不能）。
>**位置:**在CoroutineScope的CoroutineContext中或者在一个根协程中（CoroutineScope或者supervisorScope的直接子协中）。

#### Android全局协程异常获取
>**不会阻止app的崩溃，但是能获取到全局的coroutine中未处理的异常**
>1.新建文件src/main/resources/MEAT-INF/services/kotlinx.coroutines.CoroutineExceprionHandler。
>2.新建类
```class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
          override val key = CoroutineExceptionHandler

          override fun handleException(context: CoroutineContext, exception: Throwable) {
              Log.d("CoroutineException","UnHandled coroutine exception $exception")
          }
      }
```
>3.将该类路径配置到新建的文件中org.lijun.kotlin_demos.GlobalCoroutineExceptionHandler

#### 异常与取消
>1.当协程被取消的取消后不会向上传递。
>2.不能被取消的任务会在异常处理前执行。

#### 异常的聚合
>1.多个子协程发生异常时，一般取第一个异常处理。
>2.在一个异常发生后的所有异常都绑定在第一个异常之上。
>3.通过exception.suppressed.contentToString()可以活取到后面的异常。

### 协程的并发安全
**coroutine并发安全通过Atomic**
```kotlin
    fun concurrencyByAtomic() = runBlocking {
        var count = AtomicInteger(0)
        List(1000) {
            GlobalScope.launch {
                count.addAndGet(1)
            }
        }.joinAll()
        println("Atomic:$count")
    }
```
**coroutine并发安全通过Mutex**
```kotlin
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
```
**coroutine并发安全通过Semaphore**
```kotlin
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
    }  //coroutine并发安全通过Atomic
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
```
**coroutine并发安全通过Semaphore**
```kotlin
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
```
