# Flow
> 异步的进行发射收集序列的数据
## Flow的特性
> 1.Flow是一种冷流，流是连续性的。
> 2.Flow是一种类似序列的冷流，flow构建器中的代码直到被收集的时候才运行。
> 3.Flow不再被标记为suspend。
> 4.Flow{...}构建块中的代码可以挂起。
> 5.Flow流使用emit函数发射值。
> 6.Flow流使用collect函数收集值。

## Flow构建方式
**FlowF Function**
>第一种:通过flow function构建flow
```kotlin
 private fun builderFlowByFlowFunction() = flow<Int> {
     for (i in 1..3) {
         delay(1000)//挂起
         emit(i)
     }
 }

 fun collectFlowElements() = runBlocking {
     builderFlowByFlowFunction().collect() {
         println("Collect flow element:$it")
     }
 }
```

**asFlow**
>第二种：通过asFlow构建flow
```kotlin
 fun builderFlowByAsFlow() = runBlocking {
     (1..5).asFlow().filter {
         it % 2 == 0
     }.map {
         "element $it"
     }.collect() {
         println("Collect flow element:$it")
     }
 }
 ```
 **flowOf**
 >第三种：通过flowOf构建flow
```kotlin
 fun builderFlowByFlowOf() = runBlocking {
     flowOf("one", "two", "three")
         .onEach { delay(1000) }
         .collect() {
             println("Collect flow element:$it")
         }
 }
```

## Flow的上下文
> 1.流的收集总是在调用协程的上下文中发生，流的该属性成为上下文保存。
> 2.flow{...}构建器中代码必须遵循上下文保存属性，并不允许从其他上下文中发射(emit)。
> 3.flowOn操作符用于更改流的发射上下文。
```koltin
//flowOn改变流的发射空间
private fun changeFlowContext() = flow<Int>{
    for(i in 1..3){
        delay(1000)
        emit(i)
        println("emit flow element:$i Thread:${Thread.currentThread().name}")
    }
}.flowOn(Dispatchers.IO)
fun collectFlowElementByFlowOn() = runBlocking {
    changeFlowContext().collect(){
        println("Collect flow element:$it Thread:${Thread.currentThread().name}")
    }
}
```
> 4.指定流的收集协程
```kotlin
 private fun events() = (1..3).asFlow()
     .onEach { delay(1000) }
     .flowOn(Dispatchers.Default)

 fun collectFlowElementBbyLaunchIn() = runBlocking {
     events()
         .onEach { println("Collect flow element:$it Thread:${Thread.currentThread().name}") }
         .launchIn(CoroutineScope( Dispatchers.IO))
         .join()
 }
```
## Flow的取消
> 1.Flow流的构建器对每一个发射值执行附加的ensureActive检测以进行取消，通过设置流的cancelable属性就可以取消。
> 2.大多数Flow不会自行执行其他取消检测，在协程处于繁忙循环下，必须明确取消。
**取消Flow通过withTimeout**
```kotlin
  fun cancelFlowByWithTimeOut() = runBlocking {
      withTimeoutOrNull(2500) {
          simpleFlow().collect() {
              println("Collect flow element:$it")
          }
      }
      println("Done")
  }
 ```
 **取消Flow通过cancel()**
 ```kotlin
  fun cancelFlowByCancel() = runBlocking {
      simpleFlow().collect(){
          println("Collect flow element:$it")
          if(it == 3) cancel()
      }
      println("Done")
  }
```
**取消繁忙的Flow通过cancelable属性**
```kotlin
  fun cancelFlowByCancelable() = runBlocking {
      (1..5).asFlow().cancellable()
          .collect(){
              println("Collect flow element:$it")
              if(it == 3) cancel()
          }
      println("Done")
  }
```

## Flow的背压处理back pressure

**Flow处理背压通过buffer，并发运行流中发射元素的代码**
```kotlin
fun dealWithBackPressureByBuffer() = runBlocking {
    simpleFlow()
        .buffer(5)
        .collect {
            delay(1000)
            println("Collect flow element:$it")
        }
    println("Done")
}
```
**Flow处理背压通过conflate，合并发射项，不对每一个值进行处理**
```kotlin
fun dealWithBackPressureByConflate() = runBlocking {
    simpleFlow()
        .conflate()
        .collect {
            delay(1000)
            println("Collect flow element:$it")
        }
    println("Done")
}
```
**Flow处理背压通过collectLatest，取消并重新发射最后一个值**
```kotlin
fun dealWithBackPressureByCollectLatest() = runBlocking {
    simpleFlow()
        .collectLatest{
            delay(1000)
            println("Collect flow element:$it")
        }
    println("Done")
}
```

## 操作符
**操作Flow流中的元素通过Map操作符**
```kotlin
    fun operatorFlowByMap() = runBlocking {
        (1..5).asFlow().map { "Operator element by map:$it" }.collect() {
            println(it)
        }
    }
```
**操作Flow流中元素通过transform操作符**
```kotlin
    fun operatorFlowByTransform() = runBlocking {
        (1..5).asFlow().transform {
            emit("Operator element by transform:$it")
            emit("Operator element by transform emit:$it")
        }.collect() {
            println(it)
        }
    }
```
**操作Flow流中的元素通过限长操作符take**
```kotlin
    fun operatorFlowByTake() = runBlocking {
        flowOf("One", "two", "three", "four").onEach { delay(1000) }.take(3).collect() {
            println("Take flow element:$it")
        }
    }

**其他操作符**
```kotlin
    fun operatorFlowByReduceFoldToListToSetFirst() = runBlocking {
        val flow = (1..5).asFlow()
        val reduce = flow.map { it * it }.reduce() { a, b -> a + b }
        println("Reduce:${reduce}")
        val fold = flow.fold(2) { a, b -> a + b }
        println("Fold:$fold")
        val list = flow.toList()
        println("List:$list")
        val set = flow.toSet()
        println("Set:$set")
        val first = flow.first()
        println("First:$first")
    }
```
**组合Flow流通过zip**
```kotlin
    fun operatorFlowByZip() = runBlocking {
        val num = (1..3).asFlow().onEach { delay(100) }
        val str = flowOf("one", "two", "three").onEach { delay(300) }
        val startTime = System.currentTimeMillis()
        num.zip(str) { a, b -> "$a->$b" }.collect() {
            println("Collect flow:$it time:${System.currentTimeMillis() - startTime}")
        }
    }
```

## Flow流的异常捕获
**Flow流的下游异常捕获通过try**
```kotlin
    fun caughtExceptionByTry() = runBlocking {

        try {
            simpleFlow().collect() {
                println(it)
                check(it < 2) { "Check failed!" }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }
```

**的下游上游异常捕获通过catch**
```kotlin
    fun caughtExceptionByCatch() = runBlocking {
        flowOf("one", "two", "three")
            .onEach {
                delay(100)
                if (it.length > 4) throw IndexOutOfBoundsException()
            }
            .catch {
                println("Caught $it")
                emit("four")
            }
            .flowOn(Dispatchers.IO)
            .collect() {
                println("Collect element:$it")
            }
    }
```
## Flow流的完成
**Flow流的完成处理通过finally**
```kotlin
    fun flowCompleteByFinally() = runBlocking {
        try {
            (1..5).asFlow()
                .onEach {
                    delay(100)
                    check(it < 3) { println("Check: It's too big!")}
                }
                .catch { println("Caught exception:$it") }
                .collect(){
                    println("Collect element:$it")
                }
        } finally {
            println("Done")
        }
    }
```
**Flow流的完成处理通过onCompletion**
```kotlin
    fun flowCompleteByOnCompletion() = runBlocking {
        flowOf("one","two","three")
            .onEach {
                delay(100)
                check(it.length < 4)
            }
            .onCompletion {
                if(it != null){
                    println("Flow complete failed!,$it")
                }
            }
            .catch { println("Caught exception:$it") }
            .collect(){
                println("Collect element:$it")
                check(it[1] == ('t'))
            }

    }
```